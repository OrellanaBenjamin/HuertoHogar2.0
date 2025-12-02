package com.example.huertohogar20.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.huertohogar20.viewmodel.UiFeedbackViewModel

@Composable
fun FeedbackHandler(viewModel: UiFeedbackViewModel, snackbarHostState: SnackbarHostState) {
    LaunchedEffect(viewModel.snackbarMessage) {
        viewModel.snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    if (viewModel.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            title = { Text(viewModel.dialogTitle) },
            text = { Text(viewModel.dialogMsg) },
            confirmButton = {
                Button(onClick = { viewModel.dismissDialog() }) {
                    Text("OK")
                }
            }
        )
    }
}