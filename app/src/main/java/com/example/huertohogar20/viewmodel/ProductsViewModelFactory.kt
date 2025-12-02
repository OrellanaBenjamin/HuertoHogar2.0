package com.example.huertohogar20.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogar20.data.AppDatabase
import com.example.huertohogar20.data.ProductRepository

class ProductsViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    private val repository: ProductRepository by lazy {
        val dao = AppDatabase.getInstance(application).productDao()
        ProductRepository(dao)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
    }
}
