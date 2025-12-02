package com.example.huertohogar20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar20.data.CommentRepository
import com.example.huertohogar20.model.Comment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CommentRepository(application.applicationContext)

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadComments(productId: Int) {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getCommentsForProduct(productId)
                if (result.isNotEmpty()) {
                    _comments.value = result
                } else {
                    _error.value = "No hay comentarios disponibles."
                }
            } catch (e: Exception) {
                _error.value = "Error cargando comentarios: ${e.message}"
            }
            _isLoading.value = false
        }
    }

    fun addComment(productId: Int, userName: String, text: String) {
        viewModelScope.launch {
            try {
                repository.addLocalComment(productId, userName, text)
                loadComments(productId)
            } catch (e: Exception) {
                _error.value = "Error guardando comentario: ${e.message}"
            }
        }
    }
}
