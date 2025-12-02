package com.example.huertohogar20.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    // **IMPORTANTE**: Reemplaza con la URL base de tu API real.
    // Usaremos un placeholder para simular la estructura.
    private const val BASE_URL = "https://api.tudominio.com/" // Cambia esto

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val commentApiService: CommentApiService by lazy {
        retrofit.create(CommentApiService::class.java)
    }
}