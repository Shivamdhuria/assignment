package com.example.assignment.network

import com.example.assignment.domain.model.StateWrapper
import retrofit2.HttpException
import java.io.IOException

fun handleUseCaseException(e: Throwable): StateWrapper {
    e.printStackTrace()
    return when (e) {
        is IOException -> StateWrapper.NetworkError
        is HttpException -> StateWrapper.GenericError(e.code())
        else -> StateWrapper.GenericError()
    }
}