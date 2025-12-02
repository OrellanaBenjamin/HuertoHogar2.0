package com.example.huertohogar20.ui.screen

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar20.model.CartItem
import com.example.huertohogar20.model.Order
import com.example.huertohogar20.model.OrderItem
import com.example.huertohogar20.model.Product
import com.example.huertohogar20.state.globalUserProfile
import com.example.huertohogar20.ui.components.getImageResource
import com.example.huertohogar20.viewmodel.CartViewModel
import com.example.huertohogar20.viewmodel.OrderViewModel
import com.example.huertohogar20.viewmodel.ProductsViewModel
import com.example.huertohogar20.viewmodel.ProductsViewModelFactory
import kotlinx.coroutines.delay

fun validateStock(cartItems: List<CartItem>, products: List<Product>): String? {
    for (cart in cartItems) {
        val product = products.find { it.codigo == cart.productCode }
        if (product == null)
            return "Producto no encontrado: ${cart.productCode}"
        if (cart.quantity > product.stock)
            return "No hay suficiente stock de ${product.nombre}. Disponible: ${product.stock}"
    }
    return null
}

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    orderViewModel: OrderViewModel,
    userId: String,
    onCheckout: () -> Unit,
    onBuyNow: () -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    val context = LocalContext.current
    val application = context.applicationContext as Application
    val productsViewModel: ProductsViewModel = viewModel(
        factory = ProductsViewModelFactory(application)
    )
    val products by productsViewModel.products.collectAsState()

    var errorMsg by remember { mutableStateOf<String?>(null) }
    var orderSuccessMsg by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cartViewModel.loadCartItems()
        productsViewModel.loadProducts()
    }

    if (orderSuccessMsg) {
        LaunchedEffect(Unit) {
            delay(2000)
            onCheckout()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Éxito",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text("¡Pedido realizado exitosamente!", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text("Puedes ver tu boleta en el historial", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Carrito de Compras", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(10.dp))

            if (cartItems.isEmpty()) {
                Text("Tu carrito está vacío.")
                Spacer(Modifier.height(20.dp))
                Button(onClick = onBuyNow, modifier = Modifier.fillMaxWidth()) {
                    Text("Comprar")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartItems) { item ->
                        val product = products.find { it.codigo == item.productCode }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (product != null) {
                                    Image(
                                        painter = painterResource(
                                            id = getImageResource(product.imagen.trim())
                                        ),
                                        contentDescription = product.nombre,
                                        modifier = Modifier.size(64.dp)
                                    )
                                    Spacer(Modifier.width(12.dp))
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = getProductName(item.productCode, products),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Subtotal: \$${getProductPrice(item.productCode, products) * item.quantity}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        IconButton(
                                            onClick = {
                                                if (item.quantity > 1) {
                                                    cartViewModel.updateQuantity(item.productCode, item.quantity - 1)
                                                }
                                            },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Remove,
                                                contentDescription = "Disminuir"
                                            )
                                        }

                                        Text(
                                            text = "${item.quantity}",
                                            style = MaterialTheme.typography.titleMedium,
                                            modifier = Modifier.widthIn(min = 30.dp)
                                        )

                                        IconButton(
                                            onClick = {
                                                val product = products.find { it.codigo == item.productCode }
                                                if (product != null && item.quantity < product.stock) {
                                                    cartViewModel.updateQuantity(item.productCode, item.quantity + 1)
                                                }
                                            },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Aumentar"
                                            )
                                        }
                                    }
                                }

                                IconButton(onClick = { cartViewModel.remove(item) }) {
                                Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }


                if (errorMsg != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = errorMsg!!,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                }

                Button(
                    onClick = {

                        val stockError = validateStock(cartItems, products)
                        if (stockError != null) {
                            errorMsg = stockError
                            return@Button
                        }

                        val profile = globalUserProfile.value
                        if (profile?.telefono.isNullOrBlank()) {
                            errorMsg = "Debes agregar tu número de teléfono en el perfil antes de comprar"
                            return@Button
                        }
                        if (profile?.direccion.isNullOrBlank()) {
                            errorMsg = "Debes agregar tu dirección de entrega en el perfil antes de comprar"
                            return@Button
                        }


                        val order = Order(
                            userId = userId,
                            estado = "pendiente",
                            direccion = profile.direccion,
                            latitud = null,
                            longitud = null,
                            timestamp = System.currentTimeMillis(),
                        )

                        val orderItems = cartItems.map { cart ->
                            OrderItem(
                                orderId = 0,
                                productCode = cart.productCode,
                                nombre = getProductName(cart.productCode, products),
                                cantidad = cart.quantity,
                                precioUnidad = getProductPrice(cart.productCode, products)
                            )
                        }

                        orderViewModel.createOrderWithItems(order, orderItems) {
                            cartViewModel.clearCart()
                            productsViewModel.updateStockAfterOrder(orderItems)
                            errorMsg = null
                            orderSuccessMsg = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finalizar Pedido")
                }

            }
        }
    }
}

fun getProductName(productCode: String, products: List<Product>): String {
    return products.find { it.codigo == productCode }?.nombre ?: "Producto desconocido"
}

fun getProductPrice(productCode: String, products: List<Product>): Double {
    return products.find { it.codigo == productCode }?.precio ?: 0.0
}
