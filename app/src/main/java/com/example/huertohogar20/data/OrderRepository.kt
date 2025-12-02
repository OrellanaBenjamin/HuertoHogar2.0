package com.example.huertohogar20.data

import android.content.Context
import androidx.room.Room
import com.example.huertohogar20.model.Order
import com.example.huertohogar20.model.OrderItem
import kotlinx.coroutines.flow.Flow

class OrderRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "products.db"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val dao = db.orderDao()

    fun getOrdersByUserFlow(userId: String): Flow<List<Order>> =
        dao.getOrdersByUserFlow(userId)

    suspend fun getOrdersByUser(userId: String) = dao.getOrdersByUser(userId)
    suspend fun getOrderById(id: Long) = dao.getOrderById(id)

    suspend fun insertOrder(order: Order): Long {
        return dao.insert(order)
    }

    suspend fun updateOrder(order: Order) = dao.update(order)

    suspend fun insertOrderItem(item: OrderItem) {
        db.cartDao().insertOrderItem(item)
    }

    suspend fun getOrderItemsByOrderId(orderId: Long): List<OrderItem> {
        return db.cartDao().getOrderItemsByOrderId(orderId)
    }
}



