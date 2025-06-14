package com.example.fda_android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fda_android.R
import com.example.fda_android.data.CouponItem

class OfferAdapter(private val offerList : List<CouponItem>) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {
    inner class OfferViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val offerImage: ImageView = itemView.findViewById(R.id.offerImage)
        val offerTitle: TextView = itemView.findViewById(R.id.offerTitle)
        val offerDescription: TextView = itemView.findViewById(R.id.offerDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer_card, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offerList[position]
        holder.offerTitle.text = offer.discountAmount
        holder.offerDescription.text = offer.description

        Glide.with(holder.itemView.context)
            .load(offer.logo)
            .into(holder.offerImage)
    }

    override fun getItemCount(): Int = offerList.size
}