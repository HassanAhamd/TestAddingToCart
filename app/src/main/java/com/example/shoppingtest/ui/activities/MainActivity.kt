package com.example.shoppingtest.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.shoppingtest.R
import com.example.shoppingtest.viewmodels.CartSharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val cartSharedViewModel: CartSharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpNavGraph()
        observeCartItems()
    }

    private fun setUpNavGraph() {
        // Set up the nav controller for navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        // Optional: If you're using a BottomNavigationView for navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)
    }
    private fun observeCartItems() {
        lifecycleScope.launch {
            cartSharedViewModel.cartItems.collect { cartItems ->
                // Update the badge count
                updateCartBadge(cartItems.size)
            }
        }
    }
    private fun updateCartBadge(count: Int) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val cartMenuItem = bottomNavigationView.menu.findItem(R.id.cartFragment) // Ensure this ID matches your menu item ID

        // Check if a badge already exists and remove it
        bottomNavigationView.removeBadge(R.id.cartFragment)

        if (count > 0) {
            // Create a badge if there are items in the cart
            bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply {
                number = count // Set the badge count
                isVisible = true // Make the badge visible
            }
        }
    }
}