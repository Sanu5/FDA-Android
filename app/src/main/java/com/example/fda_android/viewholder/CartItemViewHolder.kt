package com.example.fda_android.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fda_android.data.ItemData
import com.example.fda_android.databinding.ItemViewCartBinding

class CartItemViewHolder(
    private val binding: ItemViewCartBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemData?, onQuantityChanged: (String?, Int) -> Unit) {
        binding.tvDishName.text = item?.itemName
        binding.tvPrice.text = "$ " + item?.itemPrice
        binding.tvQuantity.text = item?.cartItemCount.toString()

        Glide.with(binding.imgDish.context)
            .load(item?.itemImage)
            .into(binding.imgDish)

        binding.btnMinus.setOnClickListener {
            updateQuantity(item, -1, onQuantityChanged)
        }

        binding.btnPlus.setOnClickListener {
            updateQuantity(item, 1, onQuantityChanged)
        }
    }

    private fun updateQuantity(item: ItemData?, delta: Int, onQuantityChanged: (itemId: String?, newQuantity: Int) -> Unit) {
        val currentQuantity = item?.cartItemCount
        val newQuantity = maxOf(1, currentQuantity?.plus(delta) ?: 0)

        binding.tvQuantity.text = newQuantity.toString()

        onQuantityChanged(item?.itemId.toString(), newQuantity)
    }

    companion object {
        fun getViewHolder(parent: ViewGroup): CartItemViewHolder {
            return CartItemViewHolder(
                binding = ItemViewCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}