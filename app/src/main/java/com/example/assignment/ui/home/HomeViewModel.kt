package com.example.assignment.ui.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.interactors.GetNewsList
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

    init {
        Log.e("viewmodel", "intiialted")
        viewModelScope.launch {
            getNewsList.execute(apiKey).collect {
                Log.e("viewmodel", it.toString())
            }
        }
    }

}