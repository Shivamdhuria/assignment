package com.example.assignment.interactors.home

import com.example.assignment.domain.model.Article
import com.example.assignment.domain.model.data.Error
import com.example.assignment.network.NewsApiService
import com.example.assignment.network.data.MockWebServerResponses.newsListResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetNewsListTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val FAKE_TOKEN = "dsd"

    private lateinit var getNewsList: GetNewsList

    private lateinit var newsApiService: NewsApiService

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("")
        newsApiService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NewsApiService::class.java)

        //instantiate system under check
        getNewsList = GetNewsList(newsApiService)
    }

    /*
    Case for Success Response
     */
    @Test
    fun getNewsFromNetwork_emitNewsList(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(newsListResponse)
        )

        val flowItems = getNewsList.execute(FAKE_TOKEN).toList()

        //Check Loading
        assert(flowItems[0].loading)

        //check NewsList is a list with count>0
        val articles = flowItems[1].data
        assert(articles?.size ?: 0 > 0)

        // confirm they are actually Recipe objects
        assert(articles?.get(index = 0) is Article)

        // loading should be false now
        assert(!flowItems[1].loading)
    }

    /*
    Case for Error Response
    */
    @Test
    fun getNewsFromNetwork_emitHttpException(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = getNewsList.execute(FAKE_TOKEN).toList()

        //Check Loading
        assert(flowItems[0].loading)

        //check Generic Error is returned
        val error = flowItems[1].error
        assert(error == Error.GenericError)

        // loading should be false now
        assert(!flowItems[1].loading)
    }

    /*
   Case for Error Response
   */
    @Test
    fun getNewsFromNetwork_emitIOException(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setBody("{}")
                .setSocketPolicy(SocketPolicy.DISCONNECT_DURING_RESPONSE_BODY)
        )


        val flowItems = getNewsList.execute(FAKE_TOKEN).toList()

        //Check Loading
        assert(flowItems[0].loading)

        //check Generic Error is returned
        val error = flowItems[1].error
        assert(error == Error.NetworkError)

        // loading should be false now
        assert(!flowItems[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}