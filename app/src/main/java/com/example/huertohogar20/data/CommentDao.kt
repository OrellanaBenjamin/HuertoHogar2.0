package com.example.huertohogar20.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.huertohogar20.model.Comment

@Dao
interface CommentDao {

    @Query("SELECT * FROM comments WHERE productId = :productId ORDER BY createdAt DESC")
    suspend fun getCommentsForProduct(productId: Int): List<Comment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comment: Comment)
}
