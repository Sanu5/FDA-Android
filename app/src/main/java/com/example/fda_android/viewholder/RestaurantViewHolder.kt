package com.example.fda_android.viewholder

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fda_android.data.RestaurantItem
import com.example.fda_android.databinding.RestaurantListItemBinding
import eightbitlab.com.blurview.RenderEffectBlur

class RestaurantViewHolder(val binding: RestaurantListItemBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun getViewHolder(parent: ViewGroup): RestaurantViewHolder {
            return RestaurantViewHolder(
                binding = RestaurantListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    fun bind(item: RestaurantItem, onClick: (String) -> Unit) {
        binding.textTitle.text = item.name
        binding.textCuisine.text = item.type
        binding.textRating.text = item.rating.toString()
        binding.textFee.text = "$ " + item.deliveryFee + " Delivery Fee"

        Glide.with(binding.imageFood.context).clear(binding.imageFood)

        Glide.with(binding.imageFood.context)
            .load(item.image)
            .into(binding.imageFood)

        binding.root.setOnClickListener {
            onClick(item.id.toString())
        }
    }
}