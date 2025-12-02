package com.example.huertohogar20.model

import com.example.huertohogar20.R

data class UserProfile(
    val nombre: String = "",
    val apellido: String = "",
    val telefono: String = "",
    val direccion: String = "",
    val photoUri: String = "",
    val avatarId: Int = R.drawable.avatar_default
)
