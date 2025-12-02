package com.example.huertohogar20.data

import com.example.huertohogar20.model.Product
import com.example.huertohogar20.model.mockProducts
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val dao: ProductDao) {

    fun getAllProducts(): Flow<List<Product>> = dao.getAllProductsFlow()

    suspend fun insertProduct(product: Product) = dao.insert(product)
    suspend fun updateProduct(product: Product) = dao.update(product)
    suspend fun deleteProduct(product: Product) = dao.delete(product)
    suspend fun deleteByCodigo(codigo: String) = dao.deleteByCodigo(codigo)

    suspend fun getProductByCode(codigo: String): Product? {
        return dao.getProductByCode(codigo)
    }

    suspend fun checkAndInitializeData() {
        if (dao.countProducts() == 0) {
            mockProducts.forEach { product ->
                dao.insert(product)
            }
        }
    }
}