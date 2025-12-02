package com.example.huertohogar20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar20.data.ProductRepository
import com.example.huertohogar20.model.OrderItem
import com.example.huertohogar20.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel(
    application: Application,
    private val repository: ProductRepository
) : AndroidViewModel(application) {

    // Estado de la lista de productos
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    // Estado para productos caros
    private val EXPENSIVE_PRICE_THRESHOLD = 100.0
    private val _expensiveProducts = MutableStateFlow<List<Product>>(emptyList())
    val expensiveProducts: StateFlow<List<Product>> = _expensiveProducts.asStateFlow()

    // Estado para el producto seleccionado (Detalle)
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            // 1. Inicializar datos si es necesario
            repository.checkAndInitializeData()

            // 2. Escuchar cambios en la base de datos (Flow)
            repository.getAllProducts().collect { listaProductos ->
                _products.value = listaProductos
                calculateExpensiveProducts(listaProductos)
            }
        }
    }

    private fun calculateExpensiveProducts(allProducts: List<Product>) {
        val expensiveList = allProducts.filter { it.precio > EXPENSIVE_PRICE_THRESHOLD }
        _expensiveProducts.value = expensiveList
    }

    // --- Operaciones CRUD ---
    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.insertProduct(product)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    fun deleteByCodigo(codigo: String) {
        viewModelScope.launch {
            repository.deleteByCodigo(codigo)
        }
    }

    // --- Lógica de Negocio ---
    fun updateStockAfterOrder(items: List<OrderItem>) {
        viewModelScope.launch {
            items.forEach { ordered ->
                val product = repository.getProductByCode(ordered.productCode)
                if (product != null) {
                    val newStock = (product.stock - ordered.cantidad).coerceAtLeast(0)
                    repository.updateProduct(product.copy(stock = newStock))
                }
            }
        }
    }

    // --- Lógica para Detalle de Producto ---
    fun loadProductByCode(code: String) {
        viewModelScope.launch {
            _selectedProduct.value = repository.getProductByCode(code)
        }
    }

    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }
}
