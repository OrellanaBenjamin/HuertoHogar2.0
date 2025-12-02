package com.example.huertohogar20.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")

data class Product(

    @PrimaryKey val codigo: String,

    val nombre: String,

    val descripcion: String,

    val precio: Double,

    val stock: Int,

    val imagen: String,

    val categoria: String

)