package com.example.assignment.di

import com.example.assignment.BuildConfig
import com.example.assignment.network.NewsApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val NEWS_API_BASE_URL = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): NewsApiService {
       return  retrofitConfiguration(client).create(NewsApiService::class.java)
    }

    private fun retrofitConfiguration(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NEWS_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor(loggingInterceptor())
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

  //Authentications Token for newsApi. Hardcoding it for now but will have to removed.
    @Singleton
    @Provides
    @Named("api_key")
    fun provideAuthToken(): String{
        return "4787926ba5134955bdfe6e4537d791f8"
    }
}