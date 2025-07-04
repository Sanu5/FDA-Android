package com.example.fda_android.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fda_android.data.RestaurantItem
import com.example.fda_android.viewholder.RestaurantViewHolder

class RestaurantAdapter (
    private var restaurantList : List<RestaurantItem>,
    private val onClick: (String?) -> Unit
) : RecyclerView.Adapter<RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(
        holder: RestaurantViewHolder,
        position: Int
    ) {
        val restaurant = restaurantList[position]
        holder.bind(restaurant, onClick)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}