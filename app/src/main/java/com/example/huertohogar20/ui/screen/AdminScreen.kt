package com.example.huertohogar20.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar20.model.Product
import com.example.huertohogar20.viewmodel.ProductsViewModel
import com.example.huertohogar20.viewmodel.ProductsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen() {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val productsViewModel: ProductsViewModel = viewModel(
        factory = ProductsViewModelFactory(application)
    )
    val products by productsViewModel.products.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var productToEdit by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        productsViewModel.loadProducts()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Panel de Administración",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(16.dp))

            Text(
                "Gestión de Productos",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    ProductAdminCard(
                        product = product,
                        onEdit = { productToEdit = it },
                        onDelete = { productsViewModel.deleteProduct(it) }
                    )
                }
            }
        }
    }

    // Dialog para agregar producto
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

    // Dialog para editar producto
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

    val imageOptions = listOf(
        "aceite_oliva.png",
        "brocoli.png",
        "espinaca.png",
        "granola.png",
        "leche_entera.png",
        "mantequilla.png",
        "manzanas_fuji.png",
        "miel_organica.png",
        "naranja_valencia.png",
        "pimientos_tricolores.png",
        "platano_cavendish.png",
        "queso_fresco.png",
        "quinua_organica.png",
        "yogur_natural.png",
        "zanahoria_organica.png",
        "zapallo.png",
        "no_disponible.png"
    )

    val categorias = listOf(
        "Frutas Frescas",
        "Verduras Orgánicas",
        "Productos Orgánicos",
        "Productos Lácteos"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (product == null) "Agregar Producto" else "Editar Producto") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código") },
                    enabled = product == null, // No editable si ya existe
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
                    maxLines = 3
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

                // Dropdown para imagen
                var expandedImg by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedImg,
                    onExpandedChange = { expandedImg = !expandedImg }
                ) {
                    OutlinedTextField(
                        value = imagen,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Imagen") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedImg) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedImg,
                        onDismissRequest = { expandedImg = false }
                    ) {
                        imageOptions.forEach { img ->
                            DropdownMenuItem(
                                text = { Text(img) },
                                onClick = {
                                    imagen = img
                                    expandedImg = false
                                }
                            )
                        }
                    }
                }

                // Dropdown para categoría
                var expandedCat by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedCat,
                    onExpandedChange = { expandedCat = !expandedCat }
                ) {
                    OutlinedTextField(
                        value = categoria,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categoría") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCat) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCat,
                        onDismissRequest = { expandedCat = false }
                    ) {
                        categorias.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    categoria = cat
                                    expandedCat = false
                                }
                            )
                        }
                    }
                }
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
