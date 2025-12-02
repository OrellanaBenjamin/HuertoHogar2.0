package com.example.huertohogar20.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val estado: String,
    val direccion: String,
    val latitud: Double?,
    val longitud: Double?,
    val timestamp: Long,
    val pagoEstado: String = "pendiente"
)
