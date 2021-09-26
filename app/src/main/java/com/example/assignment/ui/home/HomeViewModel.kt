package com.example.assignment.ui.home

import androidx.lifecycle.*
import com.example.assignment.domain.model.Article
import com.example.assignment.domain.model.data.Error
import com.example.assignment.interactors.home.GetNewsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val getNewsList: GetNewsList,
    private @Named("api_key") val apiKey: String,
) : ViewModel() {

    private val _articles: MutableLiveData<List<Article>> = MutableLiveData(emptyList())
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _error: MutableLiveData<Error> = MutableLiveData(null)

    val articles: LiveData<List<Article>> = _articles
    val isLoading: LiveData<Boolean> = _isLoading
    val error: LiveData<Error> = _error

    init {
        getArticles()
    }

    fun getArticles() {
        viewModelScope.launch {
            getNewsList.execute(apiKey).collect { it ->
                _isLoading.value = it.loading
                it.data?.let {
                    _articles.value = it
                }
                it.error?.let {
                    _error.value = it
                }
            }
        }
    }
}