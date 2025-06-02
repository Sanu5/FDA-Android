package com.example.fda_android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fda_android.R
import com.example.fda_android.data.RestaurantItem

class RestaurantAdapter (private val restaurantList : List<RestaurantItem>) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list_item, parent, false)
        return RestaurantViewHolder(view)

    }

    override fun onBindViewHolder(
        holder: RestaurantViewHolder,
        position: Int
    ) {
        val restaurant = restaurantList[position]
        holder.restaurantName.text = restaurant.name
        holder.restaurantType.text = restaurant.type
        holder.restaurantRating.text = restaurant.rating
        holder.restaurantDeliveryFee.text = restaurant.deliveryFee
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    inner class RestaurantViewHolder(restaurantView : View) : RecyclerView.ViewHolder(restaurantView){
        val restaurantName : TextView = restaurantView.findViewById(R.id.text_title)
        val restaurantType : TextView = restaurantView.findViewById(R.id.text_cuisine)
        val restaurantRating : TextView = restaurantView.findViewById(R.id.text_rating)
        val restaurantDeliveryFee : TextView = restaurantView.findViewById(R.id.text_fee)
    }
}