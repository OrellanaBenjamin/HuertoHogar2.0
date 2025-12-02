package com.example.huertohogar20.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.huertohogar20.model.UserProfile
import com.example.huertohogar20.state.globalUserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFormScreen(
    initialProfile: UserProfile = globalUserProfile.value ?: UserProfile(),
    onForgotPassword: (() -> Unit)? = null,
    onSave: (UserProfile) -> Unit = { globalUserProfile.value = it }
) {
    var nombre by remember { mutableStateOf(initialProfile.nombre) }
    var apellido by remember { mutableStateOf(initialProfile.apellido) }
    var telefono by remember { mutableStateOf(initialProfile.telefono) }
    var direccion by remember { mutableStateOf(initialProfile.direccion) }
    var photoUri by remember { mutableStateOf<Uri?>(
        if (initialProfile.photoUri.isNotEmpty()) Uri.parse(initialProfile.photoUri) else null
    ) }

    // Picker de imagen
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        photoUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Foto de perfil con opción de cambiar
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable {
                    launcher.launch(
                        androidx.activity.result.PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (photoUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photoUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(Modifier.height(8.dp))
        Text(
            text = "Toca para cambiar foto",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(Modifier.height(12.dp))
        Text(
            text = "Editar perfil",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono", style = MaterialTheme.typography.bodyMedium) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
        Spacer(Modifier.height(11.dp))

        if (onForgotPassword != null) {
            Text(
                text = "¿Olvidaste tu contraseña?",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onForgotPassword() }
            )
            Spacer(Modifier.height(18.dp))
        }

        Button(
            onClick = {
                onSave(
                    UserProfile(
                        nombre = nombre,
                        apellido = apellido,
                        telefono = telefono,
                        direccion = direccion,
                        photoUri = photoUri?.toString() ?: ""
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Guardar cambios", style = MaterialTheme.typography.titleMedium)
        }
    }
}
