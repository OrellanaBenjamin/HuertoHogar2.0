package com.example.huertohogar20.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.huertohogar20.data.ProductRepository
import com.example.huertohogar20.model.OrderItem
import com.example.huertohogar20.model.Product
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val mockRepository = mockk<ProductRepository>(relaxed = true)
    private val mockApplication = mockk<Application>(relaxed = true)

    private lateinit var viewModel: ProductsViewModel

    private val cheapProduct = Product(
        codigo = "A1",
        nombre = "Semilla Barata",
        descripcion = "Semillas de bajo coste",
        precio = 10.0,
        stock = 3,
        imagen = "path_a1",
        categoria = "Semillas"
    )
    private val expensiveProduct = Product(
        codigo = "B2",
        nombre = "Fertilizante Premium",
        descripcion = "Fertilizante de alta calidad",
        precio = 150.0,
        stock = 2,
        imagen = "path_b2",
        categoria = "Fertilizantes"
    )
    private val allProducts = listOf(cheapProduct, expensiveProduct)

    private val productsFlow = MutableSharedFlow<List<Product>>(replay = 1)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        every { mockRepository.getAllProducts() } returns productsFlow
        coEvery { mockRepository.checkAndInitializeData() } returns Unit

        viewModel = ProductsViewModel(mockApplication, mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadProducts_actualizaListaCuandoRepositorioEmite() = runTest {

        productsFlow.emit(allProducts)


        viewModel.loadProducts()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.products.value shouldContainExactly allProducts
        coVerify(exactly = 1) { mockRepository.checkAndInitializeData() }
    }

    @Test
    fun updateStockAfterOrder_disminuyeStockYCortaEnCero() = runTest {
        val orderedItems = listOf(
            OrderItem(
                orderId = 1,
                productCode = "A1",
                nombre = "Semilla Barata",
                cantidad = 2,
                precioUnidad = 10.0
            )
        )

        coEvery { mockRepository.getProductByCode("A1") } returns cheapProduct
        coEvery { mockRepository.updateProduct(any()) } returns Unit

        viewModel.updateStockAfterOrder(orderedItems)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify {
            mockRepository.updateProduct(
                cheapProduct.copy(stock = 1)
            )
        }
    }
}