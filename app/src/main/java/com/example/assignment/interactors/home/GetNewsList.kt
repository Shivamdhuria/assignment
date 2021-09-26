package com.example.assignment.interactors.home

import com.example.assignment.domain.model.Article
import com.example.assignment.domain.model.data.DataState
import com.example.assignment.network.NewsApiService
import com.example.assignment.network.handleUseCaseException
import com.example.assignment.network.model.toDomainList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNewsList(private val newsApiService: NewsApiService) {

    companion object {
        const val DEFAULT_COUNTRY = "us"
    }

    fun execute(apiKey: String): Flow<DataState<List<Article>>> = flow {
        emit(DataState.loading())

        try {
            val newApiResponse = newsApiService.getNews(apiKey, DEFAULT_COUNTRY)
            val articles = newApiResponse.articles.toDomainList()
            emit(DataState.success(articles))

        } catch (e: Exception) {
            emit(handleUseCaseException(e))
        }
    }
}