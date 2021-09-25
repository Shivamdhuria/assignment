package com.example.assignment.network.model

import androidx.annotation.Keep

@Keep
data class NewsApiResponseDto(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)