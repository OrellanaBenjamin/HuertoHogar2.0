package com.example.huertohogar20.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.huertohogar20.model.Sucursal
import com.example.huertohogar20.ui.components.OsmdroidSucursalMap

@Composable
fun QuienesSomosScreen() {
    val sucursales = listOf(
        Sucursal("Sucursal Santiago", "Santiago", -33.45, -70.66),
        Sucursal("Sucursal Puerto Montt", "Puerto Montt", -41.32, -72.09),
        Sucursal("Sucursal Villarrica", "Villarrica", -39.28, -71.60),
        Sucursal("Sucursal Nacimiento", "Nacimiento", -37.47, -72.64),
        Sucursal("Sucursal Viña del Mar", "Viña del Mar", -33.03, -71.55),
        Sucursal("Sucursal Valparaíso", "Valparaíso", -33.05, -71.63),
        Sucursal("Sucursal Concepción", "Concepción", -36.83, -73.05)
    )

    var selectedSucursal by remember { mutableStateOf<Sucursal?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                "Quiénes Somos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E8B57)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Nuestra Misión",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Proporcionar productos frescos y de calidad directamente desde el campo hasta la puerta de nuestros clientes, garantizando la frescura y el sabor en cada entrega. Nos comprometemos a fomentar una conexión más cercana entre los consumidores y los agricultores locales, apoyando prácticas agrícolas sostenibles.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666)
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Nuestra Visión",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Ser la tienda online líder en la distribución de productos frescos y naturales en Chile, reconocida por nuestra calidad excepcional, servicio al cliente y compromiso con la sostenibilidad.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666)
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Sobre HuertoHogar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "HuertoHogar es una tienda online dedicada a llevar la frescura y calidad de los productos del campo directamente a la puerta de nuestros clientes en Chile. Con más de 6 años de experiencia, operamos en más de 9 puntos a lo largo del país.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666)
                    )
                }
            }
        }

        item {
            Text(
                "Nuestras Sucursales",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4513)
            )
        }

        item {
            OsmdroidSucursalMap(
                sucursales = sucursales,
                selectedSucursal = selectedSucursal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )
        }

        item {
            Text(
                "Ubicaciones (toca para ver en el mapa)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E8B57)
            )
        }

        items(sucursales.size) { index ->
            SucursalItem(
                sucursal = sucursales[index],
                isSelected = selectedSucursal == sucursales[index],
                onClick = {
                    selectedSucursal = sucursales[index]
                }
            )
        }

        item {
            Text(
                "Nuestros Valores",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4513)
            )
            Spacer(Modifier.height(12.dp))
        }

        item {
            ValorItem("Frescura", "Productos directamente del campo a tu mesa")
            ValorItem("Sostenibilidad", "Comprometidos con prácticas agrícolas responsables")
            ValorItem("Calidad", "Solo los mejores productos para tu familia")
            ValorItem("Comunidad", "Apoyo a los agricultores locales chilenos")
        }

        item { Spacer(Modifier.height(20.dp)) }
    }
}

@Composable
fun SucursalItem(sucursal: Sucursal, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFE8F5E9) else Color(0xFFFEFEFE)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Ubicación",
                tint = if (isSelected) Color(0xFF2E8B57) else Color(0xFF666666),
                modifier = Modifier.size(24.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    sucursal.nombre,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color(0xFF2E8B57) else Color(0xFF333333)
                )
                Text(
                    sucursal.direccion,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF666666)
                )
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Seleccionada",
                    tint = Color(0xFF2E8B57),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ValorItem(titulo: String, descripcion: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "✓",
            color = Color(0xFF2E8B57),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Column {
            Text(
                titulo,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Text(
                descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF666666)
            )
        }
    }
}
