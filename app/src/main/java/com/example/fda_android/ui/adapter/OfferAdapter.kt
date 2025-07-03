package com.example.fda_android.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fda_android.data.CouponItem
import com.example.fda_android.viewholder.OfferViewHolder

class OfferAdapter(private var offerList : List<CouponItem>) : RecyclerView.Adapter<OfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        return OfferViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offerList[position]
        holder.bind(offer)
    }

    override fun getItemCount(): Int = offerList.size
}