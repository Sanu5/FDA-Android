package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartResponse (
    val data : CartData?,
    val success : Boolean?
) : Parcelable

@Parcelize
data class CartData (
    val restaurantData : RestaurantData?,
    val cartItemCount : String?,
    val noteForRestaurant : String?,
    val noteForDeliveryPartner : String?,
    val deliveryType : String,
    val scheduledDeliveryTime : String,
    val subtotal : String?,
    val itemData: List<ItemData>?
) : Parcelable