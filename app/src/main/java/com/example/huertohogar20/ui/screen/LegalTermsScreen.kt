package com.example.huertohogar20.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegalTermsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Términos Legales y Envíos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LegalSection(
                title = "1. Información General",
                content = """
                    HuertoHogar es una tienda de productos orgánicos que opera bajo la legislación chilena, cumpliendo con la Ley N°19.496 sobre Protección de los Derechos de los Consumidores y su Reglamento de Comercio Electrónico (DS N°6 de 2021).
                    
                    RUT: XX.XXX.XXX-X
                    Dirección: [Tu dirección]
                    Email: contacto@huertohogar.cl
                    Teléfono: +56 9 XXXX XXXX
                """.trimIndent()
            )

            LegalSection(
                title = "2. Derecho a Retracto",
                content = """
                    Conforme a la Ley N°21.398 (Ley Pro-Consumidor), usted tiene derecho a retractarse de su compra dentro de 10 días corridos desde la recepción del producto, sin expresión de causa.
                    
                    Condiciones:
                    • El producto debe estar en su estado original
                    • No debe haber sido consumido ni utilizado
                    • Debe conservar su embalaje original
                    • Los productos perecibles están excluidos de este derecho
                    
                    Para ejercer este derecho, contacte a contacto@huertohogar.cl indicando número de pedido y motivo.
                """.trimIndent()
            )

            LegalSection(
                title = "3. Política de Envíos",
                content = """
                    Cobertura: Región Metropolitana y regiones principales de Chile
                    
                    Plazos de entrega:
                    • Región Metropolitana: 2-3 días hábiles
                    • Regiones: 3-5 días hábiles
                    
                    Costos de envío:
                    • Compras sobre $30.000: Envío GRATIS
                    • Compras bajo $30.000: $3.500
                    
                    El transporte de productos orgánicos se realiza cumpliendo normativas sanitarias vigentes del Ministerio de Salud, en vehículos refrigerados que garantizan la cadena de frío.
                """.trimIndent()
            )

            LegalSection(
                title = "4. Garantía Legal",
                content = """
                    Todos nuestros productos cuentan con garantía legal de 3 meses desde la recepción, conforme al artículo 21 de la Ley del Consumidor.
                    
                    La garantía cubre:
                    • Productos en mal estado al momento de recepción
                    • Productos que no cumplan con la descripción ofrecida
                    • Defectos de fabricación o envasado
                    
                    Productos perecibles: La garantía aplica solo si se reporta dentro de las 24 horas posteriores a la recepción.
                """.trimIndent()
            )

            LegalSection(
                title = "5. Devoluciones y Reembolsos",
                content = """
                    Si ejerce su derecho a retracto o la garantía legal, procesaremos el reembolso dentro de 10 días hábiles mediante:
                    • Transferencia bancaria
                    • Devolución a tarjeta de crédito/débito
                    • Nota de crédito para futuras compras
                    
                    Los costos de envío de devolución corren por cuenta del cliente, excepto en casos de productos defectuosos o error en el pedido.
                """.trimIndent()
            )

            LegalSection(
                title = "6. Protección de Datos Personales",
                content = """
                    Conforme a la Ley N°19.628 sobre Protección de la Vida Privada, sus datos personales serán utilizados exclusivamente para:
                    • Procesamiento de pedidos
                    • Envío de productos
                    • Comunicaciones sobre su compra
                    • Mejora de nuestros servicios (con su consentimiento)
                    
                    Puede solicitar acceso, modificación o eliminación de sus datos en cualquier momento escribiendo a contacto@huertohogar.cl
                """.trimIndent()
            )

            LegalSection(
                title = "7. Mediación y Resolución de Conflictos",
                content = """
                    En caso de controversias, el consumidor puede recurrir a:
                    
                    SERNAC (Servicio Nacional del Consumidor)
                    Web: www.sernac.cl
                    Teléfono: 800 700 100
                    
                    Tribunales competentes: Juzgados de Policía Local del domicilio del consumidor.
                """.trimIndent()
            )

            LegalSection(
                title = "8. Certificación de Productos Orgánicos",
                content = """
                    Todos nuestros productos orgánicos cumplen con las normas del SAG (Servicio Agrícola y Ganadero) y están certificados bajo la Ley N°20.089 sobre Sistema Nacional de Certificación de Productos Orgánicos Agrícolas.
                    
                    Nuestros proveedores están debidamente certificados y registrados ante el SAG.
                """.trimIndent()
            )

            Text(
                text = "Última actualización: Diciembre 2025",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun LegalSection(title: String, content: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Divider()
    }
}
