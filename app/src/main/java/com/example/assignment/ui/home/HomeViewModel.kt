package com.example.assignment.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.assignment.domain.model.Article
import com.example.assignment.domain.model.StateWrapper
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
    private @Named("api_key") val apiKey: String, ) : ViewModel() {

    private val _state: MutableLiveData<StateWrapper> = MutableLiveData(StateWrapper.Loading())
    val state: LiveData<StateWrapper> = _state

    init {
        getArticles()
    }

    fun getArticles(){
        viewModelScope.launch {
            getNewsList.execute(apiKey).collect {
                _state.value = it
            }
        }
    }
}