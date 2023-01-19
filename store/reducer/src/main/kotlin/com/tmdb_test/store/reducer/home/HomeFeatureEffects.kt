package com.tmdb_test.store.reducer.home

import com.tmdb_test.data.model.mapping.movie.MoviesApiToDataStateMapper
import com.tmdb_test.data.model.state.DataState
import com.tmdb_test.store.action.home.HomeAction.MovieSectionsLoaded
import com.tmdb_test.store.base.Action
import com.tmdb_test.store.base.Effect
import com.tmdb_test.store.base.Effects
import com.tmdb_test.store.env.AppEnv
import com.tmdb_test.store.feature.home.HomeFeature
import com.tmdb_test.utill.di.modules.DispatchersModule.DispatcherIo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


class HomeFeatureEffects(@DispatcherIo private val dispatcher: CoroutineDispatcher) {
    fun loadMovieSections(
        mapper: MoviesApiToDataStateMapper
    ) = mainEffect {
        withContext(dispatcher) {
            val nowPlayingMovies = async { env.network.movieSource.nowPlayingMovies() }.await()
            val nowPopularMovies = async { env.network.movieSource.nowPopularMovies() }.await()
            val topRatedMovies = async { env.network.movieSource.topRatedMovies() }.await()
            val upcomingMovies = async { env.network.movieSource.upcomingMovies() }.await()

            val mappedNowPlayingMovies = mapper(nowPlayingMovies)
            val mappedNowPopularMovies = mapper(nowPopularMovies)
            val mappedTopRatedMovies = mapper(topRatedMovies)
            val mappedUpcomingMovies = mapper(upcomingMovies)

            env.database.movieSource.insertByCategories(
                nowPlaying = mappedNowPlayingMovies.getDataIfSuccessOrDefault(),
                nowPopular = mappedNowPopularMovies.getDataIfSuccessOrDefault(),
                topRatedMovies = mappedTopRatedMovies.getDataIfSuccessOrDefault(),
                upcomingMovies = mappedUpcomingMovies.getDataIfSuccessOrDefault(),
            )

            MovieSectionsLoaded(
                nowPlayingMovies = mappedNowPlayingMovies,
                nowPopularMovies = mappedNowPopularMovies,
                topRatedMovies = mappedTopRatedMovies,
                upcomingMovies = mappedUpcomingMovies,
            )
        }
    }

    private fun <T> DataState<List<T>>.getDataIfSuccessOrDefault(): List<T> {
        return if(this.isSuccess) (this as DataState.Success).data else listOf()
    }

    private fun mainEffect(
        effectExecutorScope: suspend Effect.Executor.Scope<AppEnv>.() -> Action
    ): Effect<AppEnv> = Effects.effect(effectExecutorScope, HomeFeature)
}
