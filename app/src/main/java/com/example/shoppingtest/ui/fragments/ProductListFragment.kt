package com.example.shoppingtest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingtest.R
import com.example.shoppingtest.adapter.ProductAdapter
import com.example.shoppingtest.databinding.FragmentProductListBinding
import com.example.shoppingtest.viewmodels.CartSharedViewModel
import com.example.shoppingtest.viewmodels.ProductViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    @Inject lateinit var productAdapter: ProductAdapter  // Inject the adapter
    lateinit var mBinding: FragmentProductListBinding
    private val productViewModel: ProductViewModel by viewModels()
    private val cartSharedViewModel: CartSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentProductListBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        initObservers()

    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartSharedViewModel.cartItems.collectLatest { cartProducts ->
                    productAdapter.setCartProducts(cartProducts) // Update adapter with cart products
                }
            }
        }

            viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        productViewModel.products.collect { products ->
                            productAdapter.submitList(products)
                        }
                    }
                }
    }
    private fun setupAdapter() {
        // Set up GridLayoutManager with a span count of 2
        val linearLayoutManager = LinearLayoutManager(requireContext())
        mBinding.rvProductList.layoutManager = linearLayoutManager

        // Set up RecyclerView with the injected adapter
        mBinding.rvProductList.adapter = productAdapter

        // Set the product click listener to navigate to product details
        productAdapter.onProductClicked = { product ->
            // Hide the BottomNavigationView
            val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigationView.visibility = View.INVISIBLE
            val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(product.id)
            findNavController().navigate(action)
        }
        productAdapter.onProductAddToCart = { product ->
            Toast.makeText(requireContext(),"Product is add to cart",Toast.LENGTH_SHORT).show()
            cartSharedViewModel.addToCart(product)
        }
    }
}