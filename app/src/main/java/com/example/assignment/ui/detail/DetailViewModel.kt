package com.example.assignment.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.interactors.GetLikeAndComments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val getLikeAndComments: GetLikeAndComments
) : ViewModel() {

    private val _likes = MutableLiveData<String>("-")
    private val _comments = MutableLiveData<String>("-")

    internal val likes: LiveData<String> = _likes
    internal val comments: LiveData<String> = _comments

    fun loadLikeAndComments(articleUrl: String) {
        viewModelScope.launch {
            val stats = getLikeAndComments.execute(articleUrl)
            _likes.value = stats.likes.toString()
            _comments.value = stats.comments.toString()
        }
    }
}