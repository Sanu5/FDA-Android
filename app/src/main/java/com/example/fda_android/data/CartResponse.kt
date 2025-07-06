package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartResponse (
    val success : Boolean?,
    val data : CartData?
) : Parcelable

@Parcelize
data class CartData (
    val restaurantData : RestaurantCartData?,
    val cartItemCount : String?,
    val noteForRestaurant : String?,
    val noteForDeliveryPartner : String?,
    val deliveryType : String?,
    val scheduledDeliveryTime : String?,
    val subtotal : String?,
    val items: List<ItemData>?
) : Parcelable

@Parcelize
data class RestaurantCartData (
    val restaurantId: Int,
    val restaurantImage: String,
    val restaurantName: String,
    val address: String
) : Parcelable