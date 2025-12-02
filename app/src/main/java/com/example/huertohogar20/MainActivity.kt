package com.example.huertohogar20

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.huertohogar20.state.globalUserProfile
import com.example.huertohogar20.ui.components.AppBar
import com.example.huertohogar20.ui.components.Footer
import com.example.huertohogar20.ui.components.Navbar
import com.example.huertohogar20.ui.screen.*
import com.example.huertohogar20.ui.theme.LaHuertaHogarTheme
import com.example.huertohogar20.viewmodel.AuthViewModel
import com.example.huertohogar20.viewmodel.CartViewModel
import com.example.huertohogar20.viewmodel.CartViewModelFactory
import com.example.huertohogar20.viewmodel.OrderViewModel
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().apply {
            userAgentValue = "HuertoHogar/1.0 (Android App)"
            osmdroidBasePath = applicationContext.filesDir
        }

        enableEdgeToEdge()
        setContent {
            LaHuertaHogarTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val application = LocalContext.current.applicationContext as Application

    val cartViewModel: CartViewModel = viewModel(
        factory = CartViewModelFactory(application)
    )
    val orderViewModel: OrderViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    val orders by orderViewModel.orders.collectAsState()
    val cartCount by cartViewModel.cartItems.collectAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "welcome"

    Scaffold(
        topBar = {
            if (currentRoute !in listOf("welcome", "login", "register")) {
                AppBar(titleForRoute(currentRoute))
            }
        },
        bottomBar = {
            if (currentRoute !in listOf("welcome", "login", "register")) {
                Column {
                    Navbar(navController, currentRoute, cartCount.size)
                    Footer(numeroEmpresa = "+56 9 XXXX XXXX")
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("welcome") {
                WelcomeScreen(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
            composable("home") {
                HomeScreen(navController = navController)
            }

            composable("catalogo") {
                CatalogScreen(navController = navController)
            }
            composable("perfil") {
                ProfileScreen(
                    orders = orders,
                    navController = navController,
                    authViewModel = authViewModel,
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate("welcome") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    }
                )
            }

            composable("edit_profile") {
                EditProfileScreen(
                    onSave = { updatedProfile ->
                        globalUserProfile.value = updatedProfile
                        navController.popBackStack()
                    },
                    onCancel = {
                        navController.popBackStack()
                    }
                )
            }
            composable("carrito") {
                val userId = authViewModel.currentUser.value?.email ?: "invitado"
                CartScreen(
                    cartViewModel = cartViewModel,
                    orderViewModel = orderViewModel,
                    userId = userId,
                    onCheckout = { navController.navigate("mis_pedidos") },
                    onBuyNow = { navController.navigate("catalogo") }
                )
            }
            composable("quienessomos") {
                QuienesSomosScreen()
            }
            composable("error") {
                ErrorScreen(
                    mensaje = "Si tienes una falla, llama o manda WhatsApp al +56 9 XXXX XXXX",
                    onRetry = { navController.popBackStack() },
                    numeroEmpresa = "+56964677335"
                )
            }
            composable("mis_pedidos") {
                val userId = authViewModel.currentUser.value?.email ?: "invitado"
                LaunchedEffect(userId) { orderViewModel.loadOrders(userId) }
                val ordersList by orderViewModel.orders.collectAsState()
                OrdersListScreen(
                    orders = ordersList,
                    onOrderClick = { pedido ->
                        navController.navigate("tracking/${pedido.id}")
                    }
                )
            }
            composable("tracking/{orderId}") { backStackEntry ->
                val orderId = backStackEntry.arguments?.getString("orderId")?.toLongOrNull()
                val userId = authViewModel.currentUser.value?.email ?: "invitado"
                LaunchedEffect(orderId, userId) { orderViewModel.loadOrders(userId) }
                val ordersList by orderViewModel.orders.collectAsState()
                val pedido = ordersList.find { it.id == orderId }
                if (pedido != null) {
                    OrderTrackingScreen(
                        order = pedido,
                        navController = navController
                    )
                } else {
                    Text("Pedido no encontrado")
                }
            }
            composable("boleta/{orderId}") { backStackEntry ->
                val orderId = backStackEntry.arguments?.getString("orderId")?.toLongOrNull()
                val ordersList by orderViewModel.orders.collectAsState()
                val pedido = ordersList.find { it.id == orderId }
                if (pedido != null) {
                    ReceiptScreen(order = pedido, onBack = { navController.popBackStack() })
                } else {
                    Text("Boleta no encontrada")
                }
            }
            composable("productDetail/{code}") { backStackEntry ->
                val code = backStackEntry.arguments?.getString("code") ?: ""
                ProductDetailScreen(productCode = code)
            }
            composable("admin") {
                AdminScreen(orderViewModel = orderViewModel)
            }
        }
    }
}


fun titleForRoute(route: String): String = when (route) {
    "welcome" -> "Bienvenido"
    "login" -> "Iniciar Sesión"
    "register" -> "Registro"
    "home" -> "HuertoHogar"
    "catalogo" -> "Catálogo"
    "quienessomos" -> "Quiénes Somos"
    "perfil" -> "Perfil"
    "edit_profile" -> "Editar Perfil"
    "carrito" -> "Carrito"
    "mis_pedidos" -> "Mis Pedidos"
    "tracking/{orderId}" -> "Seguimiento"
    "boleta/{orderId}" -> "Boleta"
    else -> ""
}
