package com.example.assignment.network

import com.example.assignment.network.detail.model.CommentsDto
import com.example.assignment.network.detail.model.LikesDto
import com.example.assignment.network.model.NewsApiResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
    ): NewsApiResponseDto

    //Bypassing base url
    @GET
    suspend fun getLikes(@Url url: String): LikesDto

    //Bypassing base url
    @GET
    suspend fun getComments(@Url url: String): CommentsDto
}