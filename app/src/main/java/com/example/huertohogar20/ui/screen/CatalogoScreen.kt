package com.example.huertohogar20.ui.screen

import android.app.Application
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huertohogar20.model.categorias
import com.example.huertohogar20.ui.components.ProductCard
import com.example.huertohogar20.viewmodel.CartViewModel
import com.example.huertohogar20.viewmodel.ProductsViewModel
import com.example.huertohogar20.viewmodel.ProductsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel()
) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val productsViewModel: ProductsViewModel = viewModel(
        factory = ProductsViewModelFactory(application)
    )

    val products by productsViewModel.products.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var sortOrder by remember { mutableStateOf("ninguno") }

    LaunchedEffect(Unit) {
        productsViewModel.loadProducts()
    }


    val filteredProducts = products.filter { product ->
        val matchesSearch = searchQuery.isEmpty() ||
                product.nombre.contains(searchQuery, ignoreCase = true) ||
                product.codigo.contains(searchQuery, ignoreCase = true)

        val matchesCategory = selectedCategory.isEmpty() ||
                product.categoria == selectedCategory

        matchesSearch && matchesCategory
    }


    val sortedProducts = when (sortOrder) {
        "asc" -> filteredProducts.sortedBy { it.precio }
        "desc" -> filteredProducts.sortedByDescending { it.precio }
        else -> filteredProducts
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Catálogo HuertoHogar",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(15.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar productos...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar"
                    )
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                singleLine = true
            )
            Spacer(Modifier.height(12.dp))

            Text(
                "Filtros por Categoría",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categorias.forEach { cat ->
                    FilterChip(
                        selected = cat == selectedCategory,
                        onClick = {
                            selectedCategory = if (selectedCategory == cat) "" else cat
                        },
                        label = { Text(cat) }
                    )
                }
            }
            Spacer(Modifier.height(12.dp))

            Text(
                "Ordenar por Precio",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = sortOrder == "asc",
                    onClick = {
                        sortOrder = if (sortOrder == "asc") "ninguno" else "asc"
                    },
                    label = { Text("Menor a Mayor") }
                )
                FilterChip(
                    selected = sortOrder == "desc",
                    onClick = {
                        sortOrder = if (sortOrder == "desc") "ninguno" else "desc"
                    },
                    label = { Text("Mayor a Menor") }
                )
            }
            Spacer(Modifier.height(12.dp))

            Text(
                "Resultados: ${sortedProducts.size} producto(s)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(10.dp))

            if (sortedProducts.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (products.isEmpty()) {
                        CircularProgressIndicator()
                        Text("Cargando productos...", modifier = Modifier.padding(top = 8.dp))
                    } else {
                        Text(
                            "No hay productos que coincidan con tu búsqueda",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(sortedProducts) { producto ->
                        ProductCard(
                            product = producto,
                            onAddToCart = { prod, cantidad ->
                                cartViewModel.addOrUpdate(prod.codigo, cantidad)
                            },
                            onClick = {
                                navController.navigate("productDetail/${producto.codigo}")
                            }
                        )
                    }
                }
            }
        }
    }
}
