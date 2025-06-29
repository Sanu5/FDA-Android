package com.example.fda_android.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fda_android.data.FeaturedItem
import com.example.fda_android.databinding.RestaurantMenuItemBinding

class MenuItemViewHolder(val binding: RestaurantMenuItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun getViewHolder(parent: ViewGroup): MenuItemViewHolder {
            return MenuItemViewHolder(
                binding = RestaurantMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    fun bind(item: FeaturedItem, onItemClick: (String?) -> Unit, onAddClick: (String?) -> Unit) {
        binding.menuItemTitle.text = item.itemName
        binding.tvSubtitle.text = item.itemDesc
        binding.tvPrice.text = item.itemPrice

        Glide.with(binding.rmDish.context)
            .load(item.itemImage)
            .into(binding.rmDish)

        binding.root.setOnClickListener {
            onItemClick(item.itemID)
        }

        binding.btnAdd.setOnClickListener {
            onAddClick(item.itemID)
        }
    }
}