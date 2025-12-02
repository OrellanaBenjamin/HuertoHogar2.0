package com.example.huertohogar20.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.huertohogar20.R
import com.example.huertohogar20.viewmodel.AuthViewModel

@Composable
fun WelcomeScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {
    var showLogin by remember { mutableStateOf(true) }
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    val authError by authViewModel.authError.collectAsState()

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            navController.navigate("home") {
                popUpTo("welcome") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SegmentedControlLoginRegister(showLogin) { showLogin = it }

        Spacer(Modifier.height(22.dp))

        if (authError != null) {
            Text(
                text = authError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(8.dp))
        }

        if (showLogin) {
            LoginForm(
                onLogin = { email, pass ->
                    authViewModel.login(email, pass)
                }
            )
        } else {
            RegisterForm(
                onRegister = { nombre, email, pass, repeat ->
                    authViewModel.register(nombre, email, pass, repeat)
                }
            )
        }
    }
}

@Composable
fun SegmentedControlLoginRegister(showLogin: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onChange(true) }, modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
            colors = ButtonDefaults.buttonColors(if (showLogin) Color(0xFF2E8B57) else Color.LightGray)
        ) {
            Text("Iniciar sesión", color = Color.White)
        }
        Button(
            onClick = { onChange(false) }, modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
            colors = ButtonDefaults.buttonColors(if (!showLogin) Color(0xFFFFD700) else Color.LightGray)
        ) {
            Text("Registrarse", color = Color(0xFF8B4513))
        }
    }
}


@Composable
fun LoginForm(onLogin: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showReset by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passError by remember { mutableStateOf<String?>(null) }
    var triedSubmit by remember { mutableStateOf(false) }

    val emailBorderColor by animateColorAsState(
        targetValue = if (emailError != null && triedSubmit) Color.Red else Color.Gray,
        label = "emailBorder"
    )
    val passBorderColor by animateColorAsState(
        targetValue = if (passError != null && triedSubmit) Color.Red else Color.Gray,
        label = "passBorder"
    )

    fun validate(): Boolean {
        emailError = when {
            email.isBlank() -> "El correo es obligatorio"
            !email.contains("@") -> "Formato de correo inválido"
            else -> null
        }
        passError = when {
            password.length < 6 -> "Mínimo 6 caracteres"
            else -> null
        }
        return emailError == null && passError == null
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (triedSubmit) validate()
            },
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null && triedSubmit,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = emailBorderColor,
                unfocusedBorderColor = emailBorderColor
            )
        )
        if (emailError != null && triedSubmit) {
            Text(
                text = emailError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (triedSubmit) validate()
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = passError != null && triedSubmit,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = passBorderColor,
                unfocusedBorderColor = passBorderColor
            )
        )
        if (passError != null && triedSubmit) {
            Text(
                text = passError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "¿Olvidaste tu contraseña?",
            color = Color(0xFF2E8B57),
            style = MaterialTheme.typography.bodyMedium,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { showReset = true }
        )

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = {
                triedSubmit = true
                if (validate()) {
                    onLogin(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        if (showReset) {
            var resetEmail by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { showReset = false },
                title = { Text("Recuperar contraseña") },
                text = {
                    Column {
                        Text("Ingresa tu correo electrónico y te enviaremos instrucciones para restablecer tu contraseña.")
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value = resetEmail,
                            onValueChange = { resetEmail = it },
                            label = { Text("Correo electrónico") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (resetEmail.contains("@")) {
                                // Aquí enviarías el email real
                                showReset = false
                            }
                        }
                    ) {
                        Text("Enviar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showReset = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}


@Composable
fun RegisterForm(
    onRegister: (String, String, String, String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passError by remember { mutableStateOf<String?>(null) }
    var repeatError by remember { mutableStateOf<String?>(null) }
    var triedSubmit by remember { mutableStateOf(false) }

    val nombreBorder by animateColorAsState(
        targetValue = if (nombreError != null && triedSubmit) Color.Red else Color.Gray,
        label = "nombreBorder"
    )
    val emailBorder by animateColorAsState(
        targetValue = if (emailError != null && triedSubmit) Color.Red else Color.Gray,
        label = "emailBorderReg"
    )
    val passBorder by animateColorAsState(
        targetValue = if (passError != null && triedSubmit) Color.Red else Color.Gray,
        label = "passBorderReg"
    )
    val repeatBorder by animateColorAsState(
        targetValue = if (repeatError != null && triedSubmit) Color.Red else Color.Gray,
        label = "repeatBorderReg"
    )

    fun validate(): Boolean {
        nombreError = if (nombre.isBlank()) "El nombre es obligatorio" else null
        emailError = when {
            email.isBlank() -> "El correo es obligatorio"
            !email.contains("@") -> "Formato de correo inválido"
            else -> null
        }
        passError = if (password.length < 6) "Mínimo 6 caracteres" else null
        repeatError = when {
            repeatPassword.isBlank() -> "Repite la contraseña"
            repeatPassword != password -> "Las contraseñas no coinciden"
            else -> null
        }
        return listOf(nombreError, emailError, passError, repeatError).all { it == null }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                if (triedSubmit) validate()
            },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            isError = nombreError != null && triedSubmit,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = nombreBorder,
                unfocusedBorderColor = nombreBorder
            )
        )
        if (nombreError != null && triedSubmit) {
            Text(
                text = nombreError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (triedSubmit) validate()
            },
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null && triedSubmit,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = emailBorder,
                unfocusedBorderColor = emailBorder
            )
        )
        if (emailError != null && triedSubmit) {
            Text(
                text = emailError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (triedSubmit) validate()
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = passError != null && triedSubmit,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = passBorder,
                unfocusedBorderColor = passBorder
            )
        )
        if (passError != null && triedSubmit) {
            Text(
                text = passError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = repeatPassword,
            onValueChange = {
                repeatPassword = it
                if (triedSubmit) validate()
            },
            label = { Text("Repetir contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            isError = repeatError != null && triedSubmit,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = repeatBorder,
                unfocusedBorderColor = repeatBorder
            )
        )
        if (repeatError != null && triedSubmit) {
            Text(
                text = repeatError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = {
                triedSubmit = true
                if (validate()) {
                    onRegister(nombre, email, password, repeatPassword)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}