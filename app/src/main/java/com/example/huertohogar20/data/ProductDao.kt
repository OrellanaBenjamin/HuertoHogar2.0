package com.example.huertohogar20.data

import androidx.room.*
import com.example.huertohogar20.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products ORDER BY nombre ASC")
    fun getAllProductsFlow(): Flow<List<Product>>

    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE codigo = :codigo LIMIT 1")
    suspend fun getProductByCode(codigo: String): Product?

    @Query("SELECT COUNT(codigo) FROM products")
    suspend fun countProducts(): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)


    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM products WHERE codigo = :codigo")
    suspend fun deleteByCodigo(codigo: String)
}