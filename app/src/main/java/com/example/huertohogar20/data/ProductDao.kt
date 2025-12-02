package com.example.huertohogar20.data

import androidx.room.*
import com.example.huertohogar20.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // --- Lectura ---

    // Nuevo: Usa Flow para emitir la lista de productos en tiempo real.
    @Query("SELECT * FROM products ORDER BY nombre ASC")
    fun getAllProductsFlow(): Flow<List<Product>>

    // Lectura simple sin Flow (útil para inicialización)
    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE codigo = :codigo LIMIT 1")
    suspend fun getProductByCode(codigo: String): Product?

    // Nuevo: Cuenta los productos para la inicialización de datos.
    @Query("SELECT COUNT(codigo) FROM products")
    suspend fun countProducts(): Int

    // --- Escritura / Modificación ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    // --- Eliminación ---

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM products WHERE codigo = :codigo")
    suspend fun deleteByCodigo(codigo: String)
}