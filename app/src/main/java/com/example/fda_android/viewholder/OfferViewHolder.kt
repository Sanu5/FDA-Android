package com.example.fda_android.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fda_android.data.CouponItem
import com.example.fda_android.databinding.CouponListItemBinding

class OfferViewHolder(val binding: CouponListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun getViewHolder(parent: ViewGroup): OfferViewHolder {
            return OfferViewHolder(
                binding = CouponListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    fun bind(item: CouponItem) {
        binding.offerTitle.text = item.discountAmount + " % Off"
        binding.offerDescription.text = item.description

        Glide.with(binding.offerImage.context)
            .load(item.logo)
            .into(binding.offerImage)
    }
}