package com.example.fda_android.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fda_android.data.ItemData
import com.example.fda_android.viewholder.CartItemViewHolder

class CartItemAdapter(
    private val items: List<ItemData>?,
    private val onQuantityChanged: (itemId: String?, newQuantity: Int) -> Unit
) : RecyclerView.Adapter<CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder.getViewHolder(parent, onQuantityChanged)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(items?.get(position))
    }

    override fun getItemCount(): Int = items?.size ?: 0
}
