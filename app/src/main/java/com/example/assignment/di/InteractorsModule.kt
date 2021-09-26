package com.example.assignment.di

import com.example.assignment.interactors.GetLikeAndComments
import com.example.assignment.interactors.home.GetNewsList
import com.example.assignment.network.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetNews(recipeService: NewsApiService): GetNewsList {
        return GetNewsList(recipeService)
    }

    @ViewModelScoped
    @Provides
    fun provideGetLikesAndComments(recipeService: NewsApiService): GetLikeAndComments {
        return GetLikeAndComments(
            retrofitService = recipeService,
        )
    }


}











