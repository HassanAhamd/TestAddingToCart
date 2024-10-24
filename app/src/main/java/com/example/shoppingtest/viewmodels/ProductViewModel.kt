package com.example.shoppingtest.viewmodels

import androidx.lifecycle.ViewModel
import com.example.shoppingtest.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor() : ViewModel() {
    val products = MutableStateFlow<List<Product>>(emptyList())

    init {
        // Loading dummy product data
        products.value = listOf(
            Product(1, "Laptop", 1000.0,"This is i8 Laptop with all latest features and machines"),
            Product(2, "Smartphone", 800.0),
            Product(3, "Headphones", 150.0),
            Product(4, "Shoes", 1000.0),
            Product(5, "Pen", 800.0),
            Product(6, "Charger", 150.0),
            Product(7, "iPhone", 150.0),
            Product(8, "Keyboard", 150.0),
            Product(9, "Switches", 150.0),
            Product(10, "Wires", 150.0),
        )
    }
}