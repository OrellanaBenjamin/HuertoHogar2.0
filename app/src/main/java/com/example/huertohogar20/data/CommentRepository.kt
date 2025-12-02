package com.example.huertohogar20.data

import android.content.Context
import com.example.huertohogar20.model.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentRepository(context: Context) {

    private val commentDao = AppDatabase.getInstance(context).commentDao()
    private val api = RetrofitInstance.commentApiService

    suspend fun getCommentsForProduct(productId: Int): List<Comment> =
        withContext(Dispatchers.IO) {
            val local = commentDao.getCommentsForProduct(productId)
            if (local.isNotEmpty()) {
                return@withContext local
            }

            val remote = api.getCommentsForPost(productId)
            val mapped = remote.map {
                Comment(
                    productId = productId,
                    userName = it.name,
                    text = it.body
                )
            }
            mapped.forEach { commentDao.insert(it) }
            return@withContext mapped
        }

    suspend fun addLocalComment(productId: Int, userName: String, text: String) {
        val comment = Comment(
            productId = productId,
            userName = userName,
            text = text
        )
        commentDao.insert(comment)
    }
}
