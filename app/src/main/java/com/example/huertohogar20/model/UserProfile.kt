package com.example.huertohogar20.model

import com.example.huertohogar20.R

data class UserProfile(
    val id: Long = 0,
    val nombre: String = "",
    val apellido: String = "",
    val correo: String = "",
    val telefono: String = "",
    val direccion: String = "",
    val bio: String = "",
    val avatarId: Int = R.drawable.avatar_default
)

