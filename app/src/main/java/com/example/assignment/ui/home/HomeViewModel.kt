package com.example.assignment.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.assignment.domain.model.Article
import com.example.assignment.interactors.GetNewsList
import com.example.assignment.network.model.ArticleDto
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
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _snackbar = MutableLiveData<String?>()

    val _articles: MutableLiveData<List<Article>> = MutableLiveData(ArrayList())
    val articles: LiveData<List<Article>> = _articles

    init {
        Log.e("viewmodel", "intiialted")
        viewModelScope.launch {
            getNewsList.execute(apiKey).collect {
               _articles.value = it
            }
        }
    }

}