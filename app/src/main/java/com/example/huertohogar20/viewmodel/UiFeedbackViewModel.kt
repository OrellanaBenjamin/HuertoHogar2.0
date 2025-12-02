package com.example.huertohogar20.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UiFeedbackViewModel : ViewModel() {

    var showDialog by mutableStateOf(false)
        private set

    var dialogTitle by mutableStateOf("")
        private set

    var dialogMsg by mutableStateOf("")
        private set

    var snackbarMessage by mutableStateOf<String?>(null)
        private set

    fun showSnackbar(message: String) {
        snackbarMessage = message
    }

    fun clearSnackbar() {
        snackbarMessage = null
    }

    fun showDialog(title: String, msg: String) {
        dialogTitle = title
        dialogMsg = msg
        showDialog = true
    }

    fun dismissDialog() {
        showDialog = false
    }
}
