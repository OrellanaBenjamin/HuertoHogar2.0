package com.example.huertohogar20.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar20.model.Comment
import com.example.huertohogar20.viewmodel.CommentViewModel

@Composable
fun CommentsSection(
    productId: Int
) {
    // Usamos directamente el CommentViewModel como AndroidViewModel
    val commentViewModel: CommentViewModel = viewModel()

    val comments by commentViewModel.comments.collectAsState()
    val isLoading by commentViewModel.isLoading.collectAsState()
    val error by commentViewModel.error.collectAsState()

    LaunchedEffect(productId) {
        commentViewModel.loadComments(productId)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Comentarios de Clientes",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(8.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(Modifier.size(32.dp))
                }
            }
            error != null -> {
                Text(
                    text = "No se pudieron cargar los comentarios: $error",
                    color = MaterialTheme.colorScheme.error
                )
            }
            comments.isEmpty() -> {
                Text(
                    "Sé el primero en comentar este producto.",
                    color = MaterialTheme.colorScheme.outline
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(comments) { comment ->
                        CommentItem(comment)
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = comment.userName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            // Si no tienes rating en tu modelo, mostramos solo el nombre.
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = comment.text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
