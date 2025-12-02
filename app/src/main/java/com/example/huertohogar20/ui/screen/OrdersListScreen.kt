package com.example.huertohogar20.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.huertohogar20.model.Order
import java.util.Date

@Composable
fun OrdersListScreen(
    orders: List<Order>,
    onOrderClick: (Order) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mis pedidos", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            items(orders) { order ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp)
                        .clickable { onOrderClick(order) }
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text("Dirección: ${order.direccion}")
                        Text("Estado: ${order.estado}")
                        Text("Fecha: ${Date(order.timestamp)}")
                    }
                }
            }
        }
    }
}
