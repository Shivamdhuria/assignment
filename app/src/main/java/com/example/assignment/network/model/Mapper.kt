package com.example.assignment.network.model

import com.example.assignment.domain.model.Article

fun List<ArticleDto>.toDomainList(): List<Article> {

    return this.map { it.mapToDomainModel() }
}

fun ArticleDto.mapToDomainModel(): Article {

    /*
    The domain model must contain as little nullable fields as possible
    for simpler code
     */
    return Article(
        title = this.title.orEmpty(),
        author = this.author.orEmpty(),
        description = this.description.orEmpty(),
        url = this.url,
        content = this.content.orEmpty(),
        urlToImage = this.urlToImage
    )
}
