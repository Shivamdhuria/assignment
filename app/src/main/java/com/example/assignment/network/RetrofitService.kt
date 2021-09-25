package com.example.assignment.network

import com.example.assignment.network.model.NewsApiResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
    ): NewsApiResponseDto
}