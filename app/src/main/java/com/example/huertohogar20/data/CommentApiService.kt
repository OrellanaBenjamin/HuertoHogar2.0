package com.example.huertohogar20.data

import com.example.huertohogar20.model.RemoteComment
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentApiService {

    @GET("posts/{id}/comments")
    suspend fun getCommentsForPost(
        @Path("id") postId: Int
    ): List<RemoteComment>
}