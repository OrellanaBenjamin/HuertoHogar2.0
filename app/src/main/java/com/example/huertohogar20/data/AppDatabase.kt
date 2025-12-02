package com.example.huertohogar20.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.huertohogar20.model.CartItem
import com.example.huertohogar20.model.Comment
import com.example.huertohogar20.model.Order
import com.example.huertohogar20.model.OrderItem
import com.example.huertohogar20.model.Product
import com.example.huertohogar20.model.User

@Database(
    entities = [
        Product::class,
        CartItem::class,
        Order::class,
        OrderItem::class,
        User::class,
        Comment::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "products.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}