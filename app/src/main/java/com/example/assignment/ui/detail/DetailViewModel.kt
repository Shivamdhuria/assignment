package com.example.assignment.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.domain.model.Article
import com.example.assignment.domain.model.data.Error
import com.example.assignment.interactors.detail.GetComments
import com.example.assignment.interactors.detail.GetLikes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val getLikes: GetLikes,
    private val getComments: GetComments
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _error: MutableLiveData<Error> = MutableLiveData(null)

    private val _likes = MutableLiveData<String>("-___")
    private val _comments = MutableLiveData<String>("-___")

    internal val likes: LiveData<String> = _likes
    internal val comments: LiveData<String> = _comments
    val isLoading: LiveData<Boolean> = _isLoading
    val error: LiveData<Error> = _error

    fun getLikes(articleUrl:String) {
        viewModelScope.launch {
            getLikes.execute(articleUrl).collect { it ->
                _isLoading.value = it.loading
                it.data?.let {
                    _likes.value = it.toString()
                }
                it.error?.let {
                    _error.value = it
                }
            }
        }
    }

    fun getComments(articleUrl:String) {
        viewModelScope.launch {
            getComments.execute(articleUrl).collect { it ->
                _isLoading.value = it.loading
                it.data?.let {
                    _comments.value = it.toString()
                }
                it.error?.let {
                    _error.value = it
                }
            }
        }
    }
}