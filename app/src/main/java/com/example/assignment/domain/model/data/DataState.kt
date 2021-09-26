package com.example.assignment.domain.model.data

data class DataState<out T>(
    val data: T? = null,
    val error: Error? = null,
    val loading: Boolean = false,
) {
    companion object {

        fun <T> success(data: T): DataState<T> {
            return DataState(data = data)
        }

        fun <T> error(error: Error?): DataState<T> {
            return DataState(error = error)
        }

        fun <T> loading(): DataState<T> = DataState(loading = true)
    }
}

sealed class Error {
    object GenericError : Error()
    object NetworkError : Error()
}






