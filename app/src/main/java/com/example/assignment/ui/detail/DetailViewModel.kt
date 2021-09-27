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

    private val _isLikesLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _isCommentsLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _likes = MutableLiveData<String>("")
    private val _comments = MutableLiveData<String>("")

    internal val likes: LiveData<String> = _likes
    internal val comments: LiveData<String> = _comments
    val isLikesLoading: LiveData<Boolean> = _isLikesLoading
    val isCommentsLoading: LiveData<Boolean> = _isCommentsLoading

    //TODO: Handle error case and show retry in UI
    fun getLikes(articleUrl:String) {
        viewModelScope.launch {
            getLikes.execute(articleUrl).collect { it ->
                _isLikesLoading.value = it.loading
                it.data?.let {
                    _likes.value = it.toString()
                }
            }
        }
    }

    //TODO: Handle error case and show retry in UI
    fun getComments(articleUrl:String) {
        viewModelScope.launch {
            getComments.execute(articleUrl).collect { it ->
                _isCommentsLoading.value = it.loading
                it.data?.let {
                    _comments.value = it.toString()
                }
            }
        }
    }
}