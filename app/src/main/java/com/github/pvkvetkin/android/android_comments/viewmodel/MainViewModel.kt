package com.github.pvkvetkin.android.android_comments.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pvkvetkin.android.android_comments.model.Comment
import com.github.pvkvetkin.android.android_comments.model.Post
import com.github.pvkvetkin.android.android_comments.network.RetrofitService
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    var posts by mutableStateOf<List<Post>>(emptyList())
        private set
    var comments by mutableStateOf<List<Comment>>(emptyList())
        private set
    var postIdForComments by mutableStateOf<Int?>(null)

    fun getPosts() {
        viewModelScope.launch {
            val response = RetrofitService.retrofitService.receivePosts()
            if (response.isSuccessful) {
                posts = response.body() ?: emptyList()
            }
        }
    }

    fun getComments(postId: Int) {
        viewModelScope.launch {
            postIdForComments = postId
            val response = RetrofitService.retrofitService.receiveComments(postId)
            if (response.isSuccessful) {
                comments = response.body() ?: emptyList()
            }
        }
    }
}
