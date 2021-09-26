package com.example.assignment.interactors

import android.util.Log
import com.example.assignment.domain.model.Article
import com.example.assignment.domain.model.StateWrapper
import com.example.assignment.network.NewsApiService
import com.example.assignment.network.handleUseCaseException
import com.example.assignment.network.model.toDomainList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNewsList(private val retrofitService: NewsApiService) {

    companion object {
        const val DEFAULT_COUNTRY = "us"
    }

    fun execute(apiKey: String): Flow<StateWrapper> = flow {
        emit(StateWrapper.Loading(true))
        try {
            val newApiResponse = retrofitService.getNews(apiKey, DEFAULT_COUNTRY)
            val articles = newApiResponse.articles.toDomainList()
            emit(StateWrapper.Success(articles))

        } catch (e: Exception) {
            emit(handleUseCaseException(e))
        }
    }
}