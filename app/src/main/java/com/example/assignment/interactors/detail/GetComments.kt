package com.example.assignment.interactors.detail

import com.example.assignment.domain.model.data.DataState
import com.example.assignment.network.NewsApiService
import com.example.assignment.network.handleUseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetComments(private val retrofitService: NewsApiService) {

    companion object {
        const val COMMENTS_URL = "https://cn-news-info-api.herokuapp.com/comments/"
    }

    suspend fun execute(articleUrl: String): Flow<DataState<Long>> = flow {
        emit(DataState.loading())

        val articleId = articleUrl.replaceFirst("https://", "").replace("/", "-")
        val likesApiResponse = retrofitService.getComments(COMMENTS_URL + articleId)

        emit(DataState.success(likesApiResponse.comments))

    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}