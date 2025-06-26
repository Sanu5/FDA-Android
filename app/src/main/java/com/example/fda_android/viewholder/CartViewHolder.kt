package com.example.fda_android.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fda_android.data.CartResponse
import com.example.fda_android.databinding.CartViewBinding

class CartViewHolder(val binding: CartViewBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object{
        fun getViewHolder(parent: ViewGroup): CartViewHolder {
            return CartViewHolder(
                binding = CartViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    fun bind(item: CartResponse) {
        val cartData = item.data
        if (cartData != null) {
            binding.restaurantNameCart.text = cartData.restaurantData?.restaurantName
            binding.restaurantAddressCart.text = cartData.restaurantData?.floatingView?.address
            binding.itemCount.text = cartData.cartItemCount
            binding.tvSubtotalValue.text = cartData.subtotal ?: "$0.00"
            binding.tvNoteHint.text = cartData.noteForRestaurant ?: ""
        }
    }
}