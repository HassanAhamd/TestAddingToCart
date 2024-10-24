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
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.shoppingtest.R
import com.example.shoppingtest.databinding.FragmentProductDetailBinding
import com.example.shoppingtest.models.Product
import com.example.shoppingtest.viewmodels.CartSharedViewModel
import com.example.shoppingtest.viewmodels.ProductViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    lateinit var mBinding: FragmentProductDetailBinding
    @Inject
    lateinit var glide: RequestManager
    private val productViewModel: ProductViewModel by viewModels()
    private val cartSharedViewModel: CartSharedViewModel by activityViewModels()
    private lateinit var product: Product // Declare the product variable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         mBinding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProductDetail()
        clickListners()
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartSharedViewModel.cartItems.collect { items ->
                    // Check if the product is already in the cart
                    if (items.any { it.id == product.id }) {
                        // Change button text to "Added to Cart"
                        mBinding.tvAddToCart.text = "Remove from Cart"
                    } else {
                        // Change button text back to "Add to Cart"
                        mBinding.tvAddToCart.text = "Add to Cart"
                    }
                }
            }
        }
    }


    private fun clickListners() {
        mBinding.rlAddToCart.setOnClickListener {
            if ((mBinding.tvAddToCart.text as String).equals("Add to Cart",true)) {
                cartSharedViewModel.addToCart(product)
                Toast.makeText(requireContext(), "Added to Cart", Toast.LENGTH_SHORT).show()
            }else{
                cartSharedViewModel.removeFromCart(product)
                Toast.makeText(requireContext(), "Removed from Cart", Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.ivBack.setOnClickListener {
            // Pop the current fragment from the back stack
            requireActivity().supportFragmentManager.popBackStack()
            // Make the BottomNavigationView visible again
            val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigationView.visibility = View.VISIBLE

        }
    }

    private fun setupProductDetail() {
        val productId = ProductDetailFragmentArgs.fromBundle(requireArguments()).productId
        product = productViewModel.products.value.first { it.id == productId }

        mBinding.tvProductName.text = product.name
        mBinding.tvTitle.text = product.name
        mBinding.tvProductPrice.text = "$${product.price}"
        mBinding.tvProductDiscriprion.text = product.description


        glide.load(product.imageUrl)
            .placeholder(R.drawable.ic_products)
            .error(R.drawable.ic_products)
            .into(mBinding.ivProductImage)


    }

}