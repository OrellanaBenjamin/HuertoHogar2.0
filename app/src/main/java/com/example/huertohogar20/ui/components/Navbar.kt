package com.example.huertohogar20.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag

data class NavbarItem(val label: String, val icon: @Composable () -> Unit, val route: String)

val navbarItems = listOf(
    NavbarItem("Inicio", { Icon(imageVector = Icons.Filled.Home, contentDescription = "Inicio") }, "home"),
    NavbarItem("Carrito", { Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Carrito") }, "carrito"),
    NavbarItem("Catálogo", {Icon(imageVector = Icons.Filled.ShoppingBag, contentDescription = "Catalogo")}, "catalogo"),
    NavbarItem("Quiénes somos", { Icon(imageVector = Icons.Filled.Info, contentDescription = "Quiénes Somos") }, "quienessomos"),
    NavbarItem("Perfil", { Icon(imageVector = Icons.Filled.Person, contentDescription = "Perfil") }, "perfil")
)


@Composable
fun Navbar(navController: NavController, currentRoute: String, cartCount: Int = 0) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 6.dp
    ) {
        navbarItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                },
                icon = {
                    if (item.route == "carrito") {
                        BadgedBox(badge = {
                            if (cartCount > 0) Badge { Text(cartCount.toString()) }
                        }) {
                            Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                        }
                    } else {
                        item.icon()
                    }
                },
                label = {
                    Text(
                        item.label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (currentRoute == item.route)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}