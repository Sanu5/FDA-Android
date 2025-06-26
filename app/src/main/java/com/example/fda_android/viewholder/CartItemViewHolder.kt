package com.example.fda_android.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fda_android.data.ItemData
import com.example.fda_android.databinding.ItemViewCartBinding

class CartItemViewHolder(
    private val binding: ItemViewCartBinding,
    private val onQuantityChanged: (itemId: String, newQuantity: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemData) {
        binding.tvDishName.text = item.itemName
        binding.tvPrice.text = item.itemPrice
        binding.tvQuantity.text = item.cartItemCount

        Glide.with(binding.imgDish.context)
            .load(item.itemImage)
            .into(binding.imgDish)

        binding.btnMinus.setOnClickListener {
            updateQuantity(item, -1)
        }

        binding.btnPlus.setOnClickListener {
            updateQuantity(item, 1)
        }
    }

    private fun updateQuantity(item: ItemData, delta: Int) {
        val currentQuantity = item.cartItemCount.toIntOrNull() ?: 0
        val newQuantity = maxOf(1, currentQuantity + delta)

        binding.tvQuantity.text = newQuantity.toString()

        onQuantityChanged(item.itemId, newQuantity)
    }

    companion object {
        fun getViewHolder(
            parent: ViewGroup,
            onQuantityChanged: (itemId: String, newQuantity: Int) -> Unit
        ): CartItemViewHolder {
            val binding = ItemViewCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CartItemViewHolder(binding, onQuantityChanged)
        }
    }
}