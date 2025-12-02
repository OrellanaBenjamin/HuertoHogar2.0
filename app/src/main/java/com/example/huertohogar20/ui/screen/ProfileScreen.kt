package com.example.huertohogar20.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogar20.model.Order
import com.example.huertohogar20.model.UserProfile
import com.example.huertohogar20.state.globalUserProfile
import com.example.huertohogar20.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    orders: List<Order>,
    navController: NavController,
    authViewModel: AuthViewModel,
    onEditProfile: (() -> Unit)? = null,
    onLogout: (() -> Unit)? = null
) {
    val profile = globalUserProfile.value ?: UserProfile()
    val currentUser by authViewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Foto de perfil
        Image(
            painter = painterResource(id = profile.avatarId),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.height(16.dp))

        // Información del usuario
        Text(
            text = profile.nombre,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = profile.correo,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Teléfono: ${profile.telefono}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Dirección: ${profile.direccion}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Bio: ${profile.bio}",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(24.dp))

        // Botón Admin (solo si es admin)
        if (currentUser?.isAdmin == true) {
            Button(
                onClick = { navController.navigate("admin") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text("Panel de Administración")
            }
            Spacer(Modifier.height(12.dp))
        }

        // Botón Editar Perfil
        Button(
            onClick = { navController.navigate("edit_profile") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar Perfil")
        }

        Spacer(Modifier.height(12.dp))

        // Botón Ver Historial
        OutlinedButton(
            onClick = { navController.navigate("mis_pedidos") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Historial de Pedidos (${orders.size})")
        }

        Spacer(Modifier.height(12.dp))

        // Botón Cerrar Sesión
        Button(
            onClick = {
                globalUserProfile.value = null
                onLogout?.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun PedidoCard(order: Order, onBoletaClick: (() -> Unit)? = null) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(Modifier.padding(10.dp)) {
            Text("Dirección: ${order.direccion}")
            Text("Estado: ${order.estado}")
            Text("Fecha: ${java.util.Date(order.timestamp)}")
            Button(
                onClick = { onBoletaClick?.invoke() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver boleta")
            }
        }
    }
}
