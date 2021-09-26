package com.example.assignment.network

import com.example.assignment.domain.model.Article
import com.example.assignment.domain.model.data.DataState
import com.example.assignment.domain.model.data.Error
import com.example.assignment.domain.model.data.Error.GenericError
import java.io.IOException

fun handleUseCaseException(e: Throwable): DataState<List<Article>> {
    e.printStackTrace()
    return when (e) {
        is IOException -> DataState.error(Error.NetworkError)
        else -> DataState.error(GenericError)
    }
}