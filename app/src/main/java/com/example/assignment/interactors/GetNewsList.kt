package com.example.assignment.interactors

import com.example.assignment.network.RetrofitService
import com.example.assignment.network.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNewsList(private val retrofitService: RetrofitService) {

    fun execute(apiKey: String): Flow<List<Article>> = flow {
        try {
            val recipes = retrofitService.getNews(apiKey, "us")
            emit(recipes.articles)

        } catch (e: Exception) {
            // There was a network issue
            e.printStackTrace()
        }
    }
}