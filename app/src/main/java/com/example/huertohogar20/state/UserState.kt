package com.example.huertohogar20.state

import androidx.compose.runtime.mutableStateOf

data class User(val nombre: String, val email: String)

var globalUser = mutableStateOf<User?>(null)
