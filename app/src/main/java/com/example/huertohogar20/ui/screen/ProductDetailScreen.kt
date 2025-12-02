package com.example.huertohogar20.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar20.model.Product
import com.example.huertohogar20.viewmodel.CommentViewModel
import com.example.huertohogar20.viewmodel.ProductsViewModel
import com.example.huertohogar20.viewmodel.ProductsViewModelFactory

@Composable
fun ProductDetailScreen(
    productCode: String
) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val productsViewModel: ProductsViewModel = viewModel(
        factory = ProductsViewModelFactory(application)
    )
    val commentViewModel: CommentViewModel = viewModel()

    val selectedProduct by productsViewModel.selectedProduct.collectAsState()
    val comments by commentViewModel.comments.collectAsState()
    val isLoading by commentViewModel.isLoading.collectAsState()
    val error by commentViewModel.error.collectAsState()

    LaunchedEffect(productCode) {
        productsViewModel.loadProductByCode(productCode)
        val postId = productCode.toIntOrNull() ?: 1
        commentViewModel.loadComments(postId)
    }

    selectedProduct?.let { product ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ProductHeader(product = product)

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Comentarios (API JSONPlaceholder de prueba)",
                style = MaterialTheme.typography.titleMedium
            )

            if (isLoading) {
                CircularProgressIndicator()
            } else if (error != null) {
                Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(comments) { comment ->
                        CommentItem(
                            userName = comment.userName,
                            text = comment.text
                        )
                    }
                }
            }

            AddCommentSection(
                onSend = { name, text ->
                    val postId = productCode.toIntOrNull() ?: 1
                    commentViewModel.addComment(postId, name, text)
                }
            )
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Producto no encontrado")
    }
}

@Composable
private fun ProductHeader(product: Product) {
    Column {
        Text(text = product.nombre, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(4.dp))
        Text(text = "Código: ${product.codigo}")
        Spacer(Modifier.height(4.dp))
        Text(text = "Precio: ${product.precio}")
        Spacer(Modifier.height(4.dp))
        Text(text = "Stock: ${product.stock}")
    }
}

@Composable
private fun CommentItem(userName: String, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(text = userName, style = MaterialTheme.typography.labelMedium)
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
        Divider(modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
private fun AddCommentSection(
    onSend: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Tu nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Escribe un comentario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                if (name.isNotBlank() && text.isNotBlank()) {
                    onSend(name, text)
                    text = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar comentario")
        }
    }
}
