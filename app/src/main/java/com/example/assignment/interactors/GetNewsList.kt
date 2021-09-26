package com.example.assignment.interactors

import android.util.Log
import com.example.assignment.domain.model.Article
import com.example.assignment.network.NewsApiService
import com.example.assignment.network.model.toDomainList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNewsList(private val retrofitService: NewsApiService) {

    fun execute(apiKey: String): Flow<List<Article>> = flow {
        try {
            val newApiResponse = retrofitService.getNews(apiKey, "us")
            val articles = newApiResponse.articles.toDomainList()
            Log.e("articles,", articles.toString())
            emit(articles)

        } catch (e: Exception) {
            // There was a network issue
            e.printStackTrace()
        }
    }
}