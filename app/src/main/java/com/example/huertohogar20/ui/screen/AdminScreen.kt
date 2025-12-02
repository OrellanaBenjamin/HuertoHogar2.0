package com.example.huertohogar20.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar20.model.Order
import com.example.huertohogar20.model.Product
import com.example.huertohogar20.viewmodel.OrderViewModel
import com.example.huertohogar20.viewmodel.ProductsViewModel
import com.example.huertohogar20.viewmodel.ProductsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    orderViewModel: OrderViewModel = viewModel()
) {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val productsViewModel: ProductsViewModel = viewModel(
        factory = ProductsViewModelFactory(application)
    )

    val products by productsViewModel.products.collectAsState()
    val orders by orderViewModel.orders.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    var showAddDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        productsViewModel.loadProducts()
    }

    Scaffold(
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(onClick = { showAddDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar producto")
                }
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primaryContainer,
                tonalElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Panel de Administración",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        "Gestión completa del sistema",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Tabs
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Productos") },
                    icon = { Icon(Icons.Default.ShoppingCart, null) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Pedidos") },
                    icon = { Icon(Icons.Default.List, null) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Estadísticas") },
                    icon = { Icon(Icons.Default.Info, null) }
                )
            }

            // Content
            when (selectedTab) {
                0 -> ProductsTab(
                    products = products,
                    onEdit = { productToEdit = it },
                    onDelete = { productsViewModel.deleteProduct(it) }
                )
                1 -> OrdersTab(orders = orders)
                2 -> StatsTab(
                    totalProducts = products.size,
                    totalOrders = orders.size
                )
            }
        }
    }

    if (showAddDialog) {
        ProductFormDialog(
            product = null,
            onDismiss = { showAddDialog = false },
            onSave = { newProduct ->
                productsViewModel.addProduct(newProduct)
                showAddDialog = false
            }
        )
    }

    productToEdit?.let { product ->
        ProductFormDialog(
            product = product,
            onDismiss = { productToEdit = null },
            onSave = { updatedProduct ->
                productsViewModel.updateProduct(updatedProduct)
                productToEdit = null
            }
        )
    }
}

@Composable
fun ProductsTab(
    products: List<Product>,
    onEdit: (Product) -> Unit,
    onDelete: (Product) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Total: ${products.size} productos",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Stock total: ${products.sumOf { it.stock }}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductAdminCard(
                    product = product,
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        }
    }
}

@Composable
fun ProductAdminCard(
    product: Product,
    onEdit: (Product) -> Unit,
    onDelete: (Product) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Código: ${product.codigo}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Precio: $${product.precio} | Stock: ${product.stock}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row {
                IconButton(onClick = { onEdit(product) }) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { onDelete(product) }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun OrdersTab(orders: List<Order>) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Total de pedidos: ${orders.size}",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(12.dp))

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No hay pedidos registrados",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(orders) { order ->
                    OrderAdminCard(order)
                }
            }
        }
    }
}

@Composable
fun OrderAdminCard(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Pedido #${order.id}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = order.estado,
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (order.estado) {
                        "Entregado" -> MaterialTheme.colorScheme.primary
                        "En camino" -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.secondary
                    }
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Usuario: ${order.userId}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Dirección: ${order.direccion}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Pago: ${order.pagoEstado}",
                style = MaterialTheme.typography.bodySmall,
                color = if (order.pagoEstado == "completado")
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun StatsTab(
    totalProducts: Int,
    totalOrders: Int
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Estadísticas Generales",
            style = MaterialTheme.typography.titleLarge
        )

        StatCard(
            title = "Total Productos",
            value = totalProducts.toString(),
            icon = Icons.Default.ShoppingCart,
            color = MaterialTheme.colorScheme.primary
        )

        StatCard(
            title = "Total Pedidos",
            value = totalOrders.toString(),
            icon = Icons.Default.List,
            color = MaterialTheme.colorScheme.secondary
        )

        StatCard(
            title = "Sistema",
            value = "Activo",
            icon = Icons.Default.Info,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = color
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.displaySmall,
                    color = color
                )
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = color
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormDialog(
    product: Product?,
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit
) {
    var codigo by remember { mutableStateOf(product?.codigo ?: "") }
    var nombre by remember { mutableStateOf(product?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(product?.descripcion ?: "") }
    var precio by remember { mutableStateOf(product?.precio?.toString() ?: "") }
    var stock by remember { mutableStateOf(product?.stock?.toString() ?: "") }
    var imagen by remember { mutableStateOf(product?.imagen ?: "no_disponible.png") }
    var categoria by remember { mutableStateOf(product?.categoria ?: "Productos Orgánicos") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (product == null) "Agregar Producto" else "Editar Producto") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código") },
                    enabled = product == null,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stock") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newProduct = Product(
                        codigo = codigo,
                        nombre = nombre,
                        descripcion = descripcion,
                        precio = precio.toDoubleOrNull() ?: 0.0,
                        stock = stock.toIntOrNull() ?: 0,
                        imagen = imagen,
                        categoria = categoria
                    )
                    onSave(newProduct)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
