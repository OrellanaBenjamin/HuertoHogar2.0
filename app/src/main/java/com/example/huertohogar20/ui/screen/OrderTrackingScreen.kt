package com.example.huertohogar20.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.huertohogar20.model.Order
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun estadoColor(estado: String): Color {
    return when (estado.lowercase()) {
        "entregado" -> Color(0xFF2E8B57)
        "cancelado" -> Color(0xFFD32F2F)
        "pendiente" -> Color(0xFFFFA000)
        else -> MaterialTheme.colorScheme.primary
    } as Color
}

@Composable
fun OrderTrackingScreen(order: Order, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Seguimiento de Pedido", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = estadoColor(order.estado).copy(alpha = 0.1f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Estado: ${order.estado.uppercase()}",
                    style = MaterialTheme.typography.titleMedium,
                    color = estadoColor(order.estado)
                )
                Spacer(Modifier.height(8.dp))
                Text("Dirección: ${order.direccion}", style = MaterialTheme.typography.bodyMedium)
            }
        }
        Spacer(Modifier.height(16.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Estado de Pago",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))
                if (order.estado.lowercase().contains("pago") || order.pagoEstado == "pagado") {
                    Text(
                        "✓ Pago realizado con MercadoPago",
                        color = Color(0xFF2E8B57),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        "⏳ El pago aún está pendiente",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))

        if (!order.estado.lowercase().contains("pago") && order.pagoEstado != "pagado") {
            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Pagar con MercadoPago") }
            Spacer(Modifier.height(12.dp))
        }

        Button(
            onClick = { navController.navigate("boleta/${order.id}") },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Ver boleta") }
    }
}
