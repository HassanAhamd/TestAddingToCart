package com.example.shoppingtest.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shoppingtest.R
import com.example.shoppingtest.databinding.CartItemBinding
import com.example.shoppingtest.databinding.ProductItemBinding
import com.example.shoppingtest.models.Product
import javax.inject.Inject

private val cartDiffUtils: DiffUtil.ItemCallback<Product> =
    object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product, newItem: Product
        ): Boolean {
            return oldItem.name == newItem.name

        }

        override fun areContentsTheSame(
            oldItem: Product, newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }

class CartAdapter @Inject constructor(
    val mContext: Activity,
    val glide: RequestManager
) : ListAdapter<Product, CartAdapter.ViewHolder>(cartDiffUtils) {

    var onRemoveProduct: ((Product) -> Unit)? = null // Click listener variable

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CartItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val product = getItem(position)
            productName.text = product.name
            productPrice.text = "$${product.price}"

            glide.load(product.imageUrl)
                .placeholder(R.drawable.ic_products)
                .error(R.drawable.ic_products)
                .into(productImage)

            tvAddToCart.text ="Remove form Cart"

            rlRemove.setOnClickListener {
                onRemoveProduct?.invoke(product)
            }

        }
    }

    inner class ViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)
}