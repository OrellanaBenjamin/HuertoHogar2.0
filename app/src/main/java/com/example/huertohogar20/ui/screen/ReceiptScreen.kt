package com.example.huertohogar20.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.huertohogar20.data.OrderRepository
import com.example.huertohogar20.model.Order
import com.example.huertohogar20.model.OrderItem
import java.util.Date

@Composable
fun ReceiptScreen(
    order: Order,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val orderRepository = remember { OrderRepository(context) }
    val orderItems by produceState<List<OrderItem>>(initialValue = emptyList(), order.id) {
        value = orderRepository.getOrderItemsByOrderId(order.id)
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Boleta Nº ${order.id}", style = MaterialTheme.typography.headlineMedium)
        Text("Dirección: ${order.direccion}")
        Text("Estado: ${order.estado}")
        Text("Fecha: ${Date(order.timestamp)}")
        Spacer(Modifier.height(18.dp))
        Text("Productos:", style = MaterialTheme.typography.titleMedium)

        if (orderItems.isEmpty()) {
            Text("No hay productos asociados al pedido...", color = MaterialTheme.colorScheme.error)
        } else {
            orderItems.forEach { item ->
                Text("- ${item.nombre}: ${item.cantidad} x $${item.precioUnidad} = $${item.cantidad * item.precioUnidad}")
            }
            val total = orderItems.sumOf { it.cantidad * it.precioUnidad }
            Spacer(Modifier.height(10.dp))
            Text("Total: $${total}", style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(Modifier.height(30.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Volver") }
    }
}
