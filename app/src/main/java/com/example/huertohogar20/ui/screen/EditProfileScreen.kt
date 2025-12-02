package com.example.huertohogar20.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.huertohogar20.model.UserProfile
import com.example.huertohogar20.state.globalUserProfile

@Composable
fun EditProfileScreen(
    onSave: (UserProfile) -> Unit,
    onCancel: () -> Unit
) {
    val profile = globalUserProfile.value ?: UserProfile()

    var nombre by remember { mutableStateOf(profile.nombre) }
    var apellido by remember { mutableStateOf(profile.apellido) }
    var telefono by remember { mutableStateOf(profile.telefono) }
    var direccion by remember { mutableStateOf(profile.direccion) }
    var photoUri by remember { mutableStateOf<Uri?>(
        if (profile.photoUri.isNotEmpty()) Uri.parse(profile.photoUri) else null
    ) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    // Picker de imagen
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        photoUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Editar Perfil", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))

        // Foto de perfil
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
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

        Text(
            text = "Toca para cambiar foto",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        if (errorMsg != null) {
            Text(errorMsg!!, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(10.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    if (nombre.isBlank()) {
                        errorMsg = "El nombre es requerido"
                        return@Button
                    }
                    if (apellido.isBlank()) {
                        errorMsg = "El apellido es requerido"
                        return@Button
                    }
                    if (telefono.isBlank()) {
                        errorMsg = "El teléfono es requerido"
                        return@Button
                    }
                    if (direccion.isBlank()) {
                        errorMsg = "La dirección es requerida"
                        return@Button
                    }

                    val updatedProfile = profile.copy(
                        nombre = nombre,
                        apellido = apellido,
                        telefono = telefono,
                        direccion = direccion,
                        photoUri = photoUri?.toString() ?: ""
                    )
                    onSave(updatedProfile)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar")
            }
        }
    }
}
