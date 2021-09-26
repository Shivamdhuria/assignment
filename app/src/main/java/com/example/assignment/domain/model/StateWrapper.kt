package com.example.assignment.domain.model

sealed class StateWrapper {
    data class Success<T>(val value: T) : StateWrapper()
    data class GenericError(val code: Int? = null) : StateWrapper()
    object NetworkError : StateWrapper()
    data class Loading(val isLoading: Boolean = true) : StateWrapper()
}