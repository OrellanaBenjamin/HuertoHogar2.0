package com.example.huertohogar20.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.huertohogar20.R
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
    var bio by remember { mutableStateOf(profile.bio) }
    var avatarId by remember { mutableStateOf(profile.avatarId) }  // NUEVO
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Editar Perfil", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))
        Text(
            "Selecciona tu avatar",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AvatarOption(
                avatarId = R.drawable.avatar_default,
                isSelected = avatarId == R.drawable.avatar_default,
                onClick = { avatarId = R.drawable.avatar_default }
            )
            AvatarOption(
                avatarId = R.drawable.avatar_alt,
                isSelected = avatarId == R.drawable.avatar_alt,
                onClick = { avatarId = R.drawable.avatar_alt }
            )
        }

        Spacer(Modifier.height(20.dp))

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

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
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
                        bio = bio,
                        avatarId = avatarId
                    )
                    onSave(updatedProfile)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }

            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("Cancelar")
            }
        }
    }
}

@Composable
fun AvatarOption(
    avatarId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }

    Card(
        modifier = Modifier
            .size(80.dp)
            .clickable { onClick() },
        shape = CircleShape,
        border = BorderStroke(
            width = if (isSelected) 4.dp else 1.dp,
            color = borderColor
        )
    ) {
        Image(
            painter = painterResource(id = avatarId),
            contentDescription = "Avatar opción",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
