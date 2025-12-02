package com.example.huertohogar20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar20.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            if (email == "admin@huertohogar.cl" && password == "admin123") {
                _currentUser.value = User(
                    id = 0,
                    nombre = "Administrador",
                    email = email,
                    password = "",
                    isAdmin = true
                )
                _isAuthenticated.value = true
                _authError.value = null
                return@launch
            }

            if (email.contains("@") && password.length >= 6) {
                _currentUser.value = User(
                    id = 0,
                    nombre = "Usuario",
                    email = email,
                    password = "",
                    isAdmin = false
                )
                _isAuthenticated.value = true
                _authError.value = null
            } else {
                _authError.value = "Credenciales incorrectas"
                _isAuthenticated.value = false
            }
        }
    }

    fun register(nombre: String, email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
            if (password != repeatPassword) {
                _authError.value = "Las contraseñas no coinciden"
                return@launch
            }

            if (email.contains("@") && password.length >= 6) {
                _currentUser.value = User(
                    id = 0,
                    nombre = nombre,
                    email = email,
                    password = "",
                    isAdmin = false
                )
                _isAuthenticated.value = true
                _authError.value = null
            } else {
                _authError.value = "Datos inválidos"
                _isAuthenticated.value = false
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _isAuthenticated.value = false
        _authError.value = null
    }
}
