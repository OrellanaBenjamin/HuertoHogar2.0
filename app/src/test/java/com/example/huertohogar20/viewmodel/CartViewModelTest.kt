package com.example.huertohogar20.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.huertohogar20.data.CartDao
import com.example.huertohogar20.data.CartRepository
import com.example.huertohogar20.model.CartItem
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val mockDao = mockk<CartDao>(relaxed = true)
    private val mockRepository = mockk<CartRepository>(relaxed = true)
    private val mockApplication = mockk<Application>(relaxed = true)

    private lateinit var viewModel: CartViewModel

    private val testCartItems = listOf(
        CartItem(id = 1, productCode = "A1", quantity = 2),
        CartItem(id = 2, productCode = "B2", quantity = 1)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)


        every { mockRepository.dao } returns mockDao
        coEvery { mockRepository.getAllCartItems() } returns testCartItems
        coEvery { mockRepository.insertOrUpdateCartItem(any(), any()) } returns Unit
        coEvery { mockRepository.deleteCartItem(any()) } returns Unit
        coEvery { mockRepository.clearCart() } returns Unit
        coEvery { mockDao.getItemByCode(any()) } returns null


        viewModel = CartViewModel(mockApplication, mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadCartItems_cargaListaDesdeRepositorio() = runTest {

        viewModel.loadCartItems()
        testDispatcher.scheduler.advanceUntilIdle()


        viewModel.cartItems.value shouldHaveSize 2
        coVerify(exactly = 1) { mockRepository.getAllCartItems() }
    }

    @Test
    fun addOrUpdate_agregaNuevoItem() = runTest {

        coEvery { mockDao.getItemByCode("C3") } returns null


        viewModel.addOrUpdate("C3", 3)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepository.insertOrUpdateCartItem("C3", 3) }
        coVerify { mockRepository.getAllCartItems() }
    }

    @Test
    fun addOrUpdate_incrementaCantidadSiYaExiste() = runTest {

        val existingItem = CartItem(id = 1, productCode = "A1", quantity = 2)
        coEvery { mockDao.getItemByCode("A1") } returns existingItem

        viewModel.addOrUpdate("A1", 1)
        testDispatcher.scheduler.advanceUntilIdle()


        coVerify { mockRepository.insertOrUpdateCartItem("A1", 3) } // 2 + 1
    }

    @Test
    fun remove_eliminaItemDelCarrito() = runTest {

        val itemToRemove = testCartItems[0]


        viewModel.remove(itemToRemove)
        testDispatcher.scheduler.advanceUntilIdle()


        coVerify(exactly = 1) { mockRepository.deleteCartItem(itemToRemove) }
        coVerify { mockRepository.getAllCartItems() }
    }

    @Test
    fun clearCart_eliminaTodosLosItems() = runTest {

        viewModel.clearCart()
        testDispatcher.scheduler.advanceUntilIdle()


        coVerify(exactly = 1) { mockRepository.clearCart() }
        coVerify { mockRepository.getAllCartItems() }
    }
}
