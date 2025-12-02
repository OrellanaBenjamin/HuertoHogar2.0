package com.example.huertohogar20.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderId: Long,
    val productCode: String,
    val nombre: String,
    val cantidad: Int,
    val precioUnidad: Double
)