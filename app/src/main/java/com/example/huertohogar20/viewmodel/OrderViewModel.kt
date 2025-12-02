package com.example.huertohogar20.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar20.data.OrderRepository
import com.example.huertohogar20.model.Order
import com.example.huertohogar20.model.OrderItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = OrderRepository(application.applicationContext)

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    fun loadOrders(userId: String) {
        viewModelScope.launch {
            repository.getOrdersByUserFlow(userId).collect { ordersList ->
                _orders.value = ordersList
            }
        }
    }

    fun createOrderWithItems(order: Order, items: List<OrderItem>, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            val orderId = repository.insertOrder(order)
            items.forEach { item ->
                repository.insertOrderItem(item.copy(orderId = orderId))
            }
            onComplete()
        }
    }
}
