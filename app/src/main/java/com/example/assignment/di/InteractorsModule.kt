package com.example.assignment.di

import com.example.assignment.interactors.GetNewsList
import com.example.assignment.network.RetrofitService
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
    fun provideGetNews(recipeService:RetrofitService): GetNewsList {
        return GetNewsList(
            retrofitService = recipeService,
        )
    }


}











