package com.tmdb_test.data.model.mapping

import com.tmdb_test.api.model.util.ApiResponse
import com.tmdb_test.api.model.util.NetworkErrorModel
import com.tmdb_test.data.model.state.DataState


internal fun <T : Any, E : NetworkErrorModel, R> ApiResponse<T, E>.mapApiToDataState(
    dataMapper: (T) -> R
): DataState<R> {
    return when (this) {
        is ApiResponse.UnknownError -> DataState.Error(this.cause)
        is ApiResponse.NetworkError -> DataState.NetworkError(this.cause)
        is ApiResponse.Success<T> -> DataState.Success(dataMapper(this.data))
        is ApiResponse.ApiError -> DataState.Error()
    }
}