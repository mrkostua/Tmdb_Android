package com.tmdb_test.ui.util

import com.tmdb_test.data.model.movie.MovieDataModel
import com.tmdb_test.ui.home.data.HomeUiData
import kotlinx.datetime.LocalDate

object ModelUtil {
    val movieDataModel = MovieDataModel(
        id = 550,
        title = "Fight Club",
        voteAverage = 7.8,
        releaseDate = LocalDate.parse("1999-10-12"),
        posterUrl = null,
    )

    val uiModelMovie = HomeUiData.Movie(
        id = 550,
        title = "Fight Club",
        averageVote = 7.8,
        releaseDate = LocalDate.parse("1999-10-12"),
        posterUrl = null
    )
}