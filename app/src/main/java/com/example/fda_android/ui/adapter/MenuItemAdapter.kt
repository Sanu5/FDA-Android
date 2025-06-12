package com.example.fda_android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fda_android.R
import com.example.fda_android.data.FeaturedItem

class MenuItemAdapter(private val items : List<FeaturedItem>) : RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MenuViewHolder,
        position: Int
    ) {
        val item = items[position]
        holder.menuItemTitle.text = item.itemName
        holder.tvSubtitle.text = item.itemDesc
        holder.tvPrice.text = item.itemPrice
        Glide.with(holder.itemView.context)
            .load(item.itemImage)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MenuViewHolder(featuredItemView : View) : RecyclerView.ViewHolder(featuredItemView){
        val menuItemTitle : TextView = featuredItemView.findViewById(R.id.menuItemTitle)
        val tvSubtitle : TextView = featuredItemView.findViewById(R.id.tvSubtitle)
        val tvPrice : TextView = featuredItemView.findViewById(R.id.tvPrice)
        val itemImage : ImageView = featuredItemView.findViewById(R.id.rmDish)
    }
}