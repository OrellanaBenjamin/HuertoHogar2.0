package com.example.huertohogar20.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Int,
    val userName: String,
    val text: String,
    val createdAt: Long = System.currentTimeMillis()
)