package com.example.shoppingtest.viewmodels

import androidx.lifecycle.ViewModel
import com.example.shoppingtest.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CartSharedViewModel @Inject constructor() : ViewModel() {
    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems

    fun addToCart(product: Product) {
        _cartItems.value += product
    }
    fun removeFromCart(product: Product) {
        _cartItems.value = _cartItems.value.filter { it.id != product.id }
    }

    val totalCost: Flow<Double> = _cartItems.map { it.sumOf { product -> product.price } }
}