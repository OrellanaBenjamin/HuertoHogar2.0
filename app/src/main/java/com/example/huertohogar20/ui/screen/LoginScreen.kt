package com.example.huertohogar20.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar20.model.UserProfile
import com.example.huertohogar20.viewmodel.UiFeedbackViewModel
import com.example.huertohogar20.ui.components.FeedbackHandler
import com.example.huertohogar20.state.globalUserProfile

@Composable
fun LoginScreen(
    onLoginSuccess: (() -> Unit)? = null,
    onRegisterNavigate: (() -> Unit)? = null,
    onForgotPassword: (() -> Unit)? = null
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showResetDialog by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val feedbackViewModel: UiFeedbackViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Iniciar sesión",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(22.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !it.contains("@") || it.length < 5
                    },
                    label = { Text("Correo electrónico") },
                    isError = emailError,
                    supportingText = {
                        if (emailError) Text(
                            "Ingresa un correo válido.",
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.length < 4
                    },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError,
                    supportingText = {
                        if (passwordError) Text(
                            "La contraseña debe tener al menos 4 caracteres.",
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { showResetDialog = true }
                )
                Spacer(Modifier.height(18.dp))

                Button(
                    onClick = {
                        if (!emailError && !passwordError && email.isNotBlank() && password.isNotBlank()) {
                            globalUserProfile.value = UserProfile(
                                nombre = "",
                                apellido = "",
                                telefono = "",
                                direccion = "",
                                photoUri = ""
                            )
                            feedbackViewModel.showSnackbar("Login exitoso")
                            onLoginSuccess?.invoke()
                        } else {
                            feedbackViewModel.showDialog("Error", "Revisa los campos marcados en rojo.")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !emailError && !passwordError
                ) {
                    Text("Iniciar sesión")
                }
                Spacer(Modifier.height(12.dp))

                TextButton(
                    onClick = { onRegisterNavigate?.invoke() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("¿No tienes cuenta? Regístrate")
                }
            }

            if (showResetDialog) {
                PasswordResetDialog(
                    onDismiss = { showResetDialog = false },
                    onConfirm = { resetEmail ->
                        feedbackViewModel.showSnackbar("Se ha enviado un correo a $resetEmail")
                        showResetDialog = false
                    }
                )
            }

            FeedbackHandler(feedbackViewModel, snackbarHostState)
        }
    }
}

@Composable
fun PasswordResetDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var resetEmail by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Recuperar Contraseña",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                if (!showSuccessMessage) {
                    Text(
                        "Ingresa tu correo electrónico y te enviaremos instrucciones para restablecer tu contraseña.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = resetEmail,
                        onValueChange = {
                            resetEmail = it
                            emailError = !it.contains("@") || it.length < 5
                        },
                        label = { Text("Correo electrónico") },
                        isError = emailError,
                        supportingText = {
                            if (emailError) Text(
                                "Ingresa un correo válido",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "✓",
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Correo enviado",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Se ha enviado un correo a:",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            resetEmail,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "¿No te ha llegado?",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        TextButton(
                            onClick = { showSuccessMessage = false }
                        ) {
                            Text("Volver a intentarlo")
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (!showSuccessMessage) {
                Button(
                    onClick = {
                        if (!emailError && resetEmail.isNotBlank()) {
                            showSuccessMessage = true
                            onConfirm(resetEmail)
                        }
                    },
                    enabled = !emailError && resetEmail.isNotBlank()
                ) {
                    Text("Enviar")
                }
            } else {
                Button(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }
        },
        dismissButton = {
            if (!showSuccessMessage) {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        }
    )
}
