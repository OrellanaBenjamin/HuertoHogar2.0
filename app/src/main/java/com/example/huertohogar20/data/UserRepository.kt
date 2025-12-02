package com.example.huertohogar20.data

import android.content.Context
import com.example.huertohogar20.model.User

class UserRepository(context: Context) {

    private val userDao = AppDatabase.getInstance(context).userDao()

    suspend fun registerUser(nombre: String, email: String, password: String): Result<User> {
        val existing = userDao.getByEmail(email)
        if (existing != null) {
            return Result.failure(Exception("El correo ya está registrado"))
        }
        val user = User(nombre = nombre, email = email, password = password)
        val id = userDao.insert(user)
        return Result.success(user.copy(id = id))
    }

    suspend fun login(email: String, password: String): Result<User> {
        val user = userDao.login(email, password)
        return if (user != null) {
            Result.success(user)
        } else {
            Result.failure(Exception("Credenciales inválidas"))
        }
    }
}
