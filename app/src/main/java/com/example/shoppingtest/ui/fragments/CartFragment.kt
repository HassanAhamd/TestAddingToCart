package com.example.shoppingtest.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingtest.adapter.CartAdapter
import com.example.shoppingtest.adapter.ProductAdapter
import com.example.shoppingtest.databinding.FragmentCartBinding
import com.example.shoppingtest.databinding.FragmentProductListBinding
import com.example.shoppingtest.viewmodels.CartSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {
    @Inject
    lateinit var cartAdapter: CartAdapter  // Inject the adapter
    lateinit var mBinding: FragmentCartBinding

    private val cartSharedViewModel: CartSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCartBinding.inflate(inflater, container, false)
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
                cartSharedViewModel.cartItems.collect { items ->
                      cartAdapter.submitList(items)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartSharedViewModel.totalCost.collectLatest { total ->
                    mBinding.totalCostTextView.text = "$$total"
                }
            }
        }
    }

    private fun setupAdapter() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        mBinding.rvCart.layoutManager = linearLayoutManager

        // Set up RecyclerView with the injected adapter
        mBinding.rvCart.adapter = cartAdapter

        cartAdapter.onRemoveProduct = { product ->
            Toast.makeText(requireContext(),"Product is removed from cart", Toast.LENGTH_SHORT).show()
            cartSharedViewModel.removeFromCart(product)
        }

    }
}