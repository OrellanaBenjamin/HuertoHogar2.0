package com.example.huertohogar20.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.huertohogar20.model.Product
import com.example.huertohogar20.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: Product,
    onAddToCart: (Product, Int) -> Unit,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    var cantidad by remember { mutableStateOf(1) }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = getImageResource(product.imagen.trim())),
                contentDescription = product.nombre,
                modifier = Modifier.size(64.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.nombre, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Text(product.descripcion, style = MaterialTheme.typography.bodySmall)
                Text("Código: ${product.codigo}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Stock: ${product.stock}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Precio: \$${product.precio}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(
                        onClick = { if (cantidad > 1) cantidad-- },
                        enabled = cantidad > 1
                    ) { Text("-") }

                    Text(
                        text = cantidad.toString(),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    OutlinedButton(
                        onClick = { if (cantidad < product.stock) cantidad++ },
                        enabled = cantidad < product.stock
                    ) { Text("+") }
                }

                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { onAddToCart(product, cantidad) },
                    enabled = cantidad in 1..product.stock,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar al carrito")
                }
            }
        }
    }
}


fun getImageResource(imagen: String): Int {
    return when(imagen) {
        "aceite_oliva.png" -> R.drawable.aceite_oliva
        "avatar_alt.png" -> R.drawable.avatar_alt
        "avatar_default.png" -> R.drawable.avatar_default
        "brocoli.png" -> R.drawable.brocoli
        "espinaca.png" -> R.drawable.espinaca
        "granola.png" -> R.drawable.granola
        "leche_entera.png" -> R.drawable.leche_entera
        "logo_huerto.png" -> R.drawable.logo_huerto
        "mantequilla.webp" -> R.drawable.mantequilla
        "manzanas_fuji.png" -> R.drawable.manzanas_fuji
        "miel_organica.png" -> R.drawable.miel_organica
        "naranja_valencia.png" -> R.drawable.naranja_valencia
        "no_disponible.png" -> R.drawable.no_disponible
        "pimientos_tricolores.png" -> R.drawable.pimientos_tricolores
        "platano_cavendish.jpg" -> R.drawable.platano_cavendish
        "queso_fresco.png" -> R.drawable.queso_fresco
        "quinua_organica.png" -> R.drawable.quinua_organica
        "yogur_natural.webp" -> R.drawable.yogur_natural
        "zanahoria_organica.png" -> R.drawable.zanahoria_organica
        "zapallo.png" -> R.drawable.zapallo
        else -> R.drawable.no_disponible
    }
}