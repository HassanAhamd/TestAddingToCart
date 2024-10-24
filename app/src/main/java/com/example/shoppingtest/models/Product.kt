package com.example.shoppingtest.models

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String = "This is default Product Description",  // Optional description field
    val imageUrl: String = ""      // Optional image field
)