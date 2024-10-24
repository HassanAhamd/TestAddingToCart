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
import com.example.shoppingtest.databinding.ProductItemBinding
import com.example.shoppingtest.models.Product
import javax.inject.Inject

private val productDiffUtilLanguage: DiffUtil.ItemCallback<Product> =
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

class ProductAdapter @Inject constructor(
    val mContext: Activity,
    val glide: RequestManager
) : ListAdapter<Product, ProductAdapter.ViewHolder>(productDiffUtilLanguage) {

    var onProductClicked: ((Product) -> Unit)? = null // Click listener variable
    var onProductAddToCart: ((Product) -> Unit)? = null // Click listener variable
    private var cartProducts: List<Product> = listOf() // List to hold cart products

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    fun setCartProducts(products: List<Product>) {
        cartProducts = products // Update the list of cart products
        notifyDataSetChanged() // Notify the adapter to refresh the UI
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

            tvAddToCart.text = if (cartProducts.contains(product)) "In Cart" else "Add to Cart"

            root.setOnClickListener {
               onProductClicked?.invoke(product)
            }
            rlAddToCart.setOnClickListener {
                if ((tvAddToCart.text as String).equals("Add to Cart",true)) {
                    onProductAddToCart?.invoke(product)
                }else{
                    Toast.makeText(mContext,"Already in Cart",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    inner class ViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
}