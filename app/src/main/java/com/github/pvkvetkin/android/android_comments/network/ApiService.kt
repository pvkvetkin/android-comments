package com.github.pvkvetkin.android.android_comments.network

import com.github.pvkvetkin.android.android_comments.model.Comment
import com.github.pvkvetkin.android.android_comments.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    suspend fun receivePosts(): Response<List<Post>>

    @GET("comments")
    suspend fun receiveComments(@Query("postId") postId: Int): Response<List<Comment>>
}
