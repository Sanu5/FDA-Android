package com.example.fda_android.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fda_android.data.FeaturedItem
import com.example.fda_android.viewholder.MenuItemViewHolder

class MenuItemAdapter(
    private val items : List<FeaturedItem>,
    private val onItemClick: (String?) -> Unit,
    private val onAddClick: (String?) -> Unit
) : RecyclerView.Adapter<MenuItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        return MenuItemViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClick, onAddClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}