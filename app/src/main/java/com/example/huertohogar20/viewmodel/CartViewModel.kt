package com.example.huertohogar20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar20.data.CartRepository
import com.example.huertohogar20.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    application: Application,
    repository: CartRepository? = null
) : AndroidViewModel(application) {


    private val repository = repository ?: CartRepository(application.applicationContext)

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun loadCartItems() {
        viewModelScope.launch {
            _cartItems.value = repository.getAllCartItems()
        }
    }

    fun addOrUpdate(productCode: String, quantity: Int) {
        viewModelScope.launch {
            val current = repository.dao.getItemByCode(productCode)
            val newQuantity = (current?.quantity ?: 0) + quantity
            repository.insertOrUpdateCartItem(productCode, newQuantity)
            loadCartItems()
        }
    }

    fun remove(item: CartItem) {
        viewModelScope.launch {
            repository.deleteCartItem(item)
            loadCartItems()
        }
    }
    fun updateQuantity(productCode: String, newQuantity: Int) {
        viewModelScope.launch {
            repository.updateQuantity(productCode, newQuantity)
            loadCartItems()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
            loadCartItems()
        }
    }
}
