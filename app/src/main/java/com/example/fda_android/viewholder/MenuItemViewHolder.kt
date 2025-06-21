package com.example.fda_android.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fda_android.data.FeaturedItem
import com.example.fda_android.databinding.RestaurantMenuItemBinding

class MenuItemViewHolder(val binding: RestaurantMenuItemBinding, onClick: (String?) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun getViewHolder(parent: ViewGroup, onClick: (String?) -> Unit): MenuItemViewHolder {
            return MenuItemViewHolder(
                binding = RestaurantMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onClick = {
                    onClick
                }
            )
        }
    }

    fun bind(item: FeaturedItem, onClick: (String?) -> Unit) {
        binding.menuItemTitle.text = item.itemName
        binding.tvSubtitle.text = item.itemDesc
        binding.tvPrice.text = item.itemPrice

        Glide.with(binding.rmDish.context)
            .load(item.itemImage)
            .into(binding.rmDish)

        binding.root.setOnClickListener {
            onClick(item.itemID)
        }
    }
}