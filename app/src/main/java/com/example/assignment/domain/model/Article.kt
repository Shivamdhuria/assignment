package com.example.assignment.domain.model

import com.example.assignment.network.model.SourceDto

data class Article(
    val author: String,
    val description: String,
    val url: String,
    val content: String,
    val urlToImage: String?
)