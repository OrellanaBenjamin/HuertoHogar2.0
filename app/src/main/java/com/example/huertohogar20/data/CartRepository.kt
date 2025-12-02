package com.example.huertohogar20.data

import android.content.Context
import androidx.room.Room
import androidx.room.util.copy
import com.example.huertohogar20.model.CartItem
import com.example.huertohogar20.data.CartDao
import com.example.huertohogar20.model.OrderItem


class CartRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "products.db"
    )
        .fallbackToDestructiveMigration()
        .build()
    val dao = db.cartDao()

    suspend fun getAllCartItems() = dao.getAll()
    suspend fun insertOrUpdateCartItem(code: String, quantity: Int) {
        val existing = dao.getItemByCode(code)
        if (existing != null) {
            dao.update(existing.copy(quantity = quantity))
        } else {
            dao.insert(CartItem(productCode = code, quantity = quantity))
        }
    }

    suspend fun deleteCartItem(cartItem: CartItem) = dao.delete(cartItem)
    suspend fun clearCart() = dao.clearCart()

    suspend fun getItemByCode(productCode: String): CartItem? = dao.getItemByCode(productCode)

    suspend fun updateQuantity(productCode: String, newQuantity: Int) {
        dao.updateQuantity(productCode, newQuantity)
    }

    suspend fun insertOrderItem(orderItem: OrderItem) {
        dao.insertOrderItem(orderItem)
    }

    suspend fun getOrderItemsByOrderId(orderId: Long): List<OrderItem> {
        return dao.getOrderItemsByOrderId(orderId)
    }
}