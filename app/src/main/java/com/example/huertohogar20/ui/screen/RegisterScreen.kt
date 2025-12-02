package com.example.huertohogar20.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar20.model.UserProfile
import com.example.huertohogar20.viewmodel.UiFeedbackViewModel
import com.example.huertohogar20.ui.components.FeedbackHandler
import com.example.huertohogar20.state.globalUserProfile

@Composable
fun RegisterScreen(
    onRegisterSuccess: (() -> Unit)? = null,
    onLoginNavigate: (() -> Unit)? = null
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var repeatError by remember { mutableStateOf(false) }
    var nombreError by remember { mutableStateOf(false) }
    var apellidoError by remember { mutableStateOf(false) }

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
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        nombreError = it.length < 3
                    },
                    label = { Text("Nombre") },
                    isError = nombreError,
                    supportingText = { if (nombreError) Text("Debe tener al menos 3 caracteres.", color = MaterialTheme.colorScheme.error) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = apellido,
                    onValueChange = {
                        apellido = it
                        apellidoError = it.length < 3
                    },
                    label = { Text("Apellido") },
                    isError = apellidoError,
                    supportingText = { if (apellidoError) Text("Debe tener al menos 3 caracteres.", color = MaterialTheme.colorScheme.error) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !it.contains("@") || it.length < 5
                    },
                    label = { Text("Correo electrónico") },
                    isError = emailError,
                    supportingText = { if (emailError) Text("Correo inválido.", color = MaterialTheme.colorScheme.error) },
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
                    supportingText = { if (passwordError) Text("Debe tener al menos 4 caracteres.", color = MaterialTheme.colorScheme.error) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = repeatPassword,
                    onValueChange = {
                        repeatPassword = it
                        repeatError = it != password
                    },
                    label = { Text("Repetir contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = repeatError,
                    supportingText = { if (repeatError) Text("Las contraseñas no coinciden.", color = MaterialTheme.colorScheme.error) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(18.dp))
                Button(
                    onClick = {
                        if (!nombreError && !apellidoError && !emailError && !passwordError && !repeatError &&
                            nombre.isNotBlank() && apellido.isNotBlank() && email.isNotBlank() && password.isNotBlank() && repeatPassword.isNotBlank()) {
                            globalUserProfile.value = UserProfile(
                                nombre = nombre,
                                apellido = apellido,
                                telefono = "",  // CORREGIDO
                                direccion = "",
                                photoUri = ""
                            )
                            feedbackViewModel.showSnackbar("Registro exitoso")
                            onRegisterSuccess?.invoke()
                        } else {
                            feedbackViewModel.showDialog("Error", "Completa los campos correctamente")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !nombreError && !apellidoError && !emailError && !passwordError && !repeatError
                ) {
                    Text("Registrarse")
                }
                Spacer(Modifier.height(12.dp))
                TextButton(
                    onClick = { onLoginNavigate?.invoke() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("¿Ya tienes cuenta? Iniciar sesión")
                }
            }
            FeedbackHandler(feedbackViewModel, snackbarHostState)
        }
    }
}
