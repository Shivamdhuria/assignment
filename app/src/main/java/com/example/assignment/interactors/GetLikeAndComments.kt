package com.example.assignment.interactors

import com.example.assignment.domain.model.Stats
import com.example.assignment.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetLikeAndComments(private val retrofitService: NewsApiService) {

    companion object {
        const val LIKES_URL = "https://cn-news-info-api.herokuapp.com/likes/"
        const val COMMENTS_URL = "https://cn-news-info-api.herokuapp.com/comments/"
    }

    suspend fun execute(articleUrl: String): Stats = withContext(Dispatchers.IO) {
        val articleId = articleUrl.replaceFirst("https://", "").replace("/", "-")

        val likesApiResponse = async { retrofitService.getLikes(LIKES_URL + articleId) }
        val commentsApiResponse = async { retrofitService.getComments(COMMENTS_URL + articleId) }
        Stats(likesApiResponse.await().likes, commentsApiResponse.await().comments)
    }
}