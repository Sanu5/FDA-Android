package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantViewResponse (
    val data : RestaurantData,
    val success : Boolean
) : Parcelable

@Parcelize
data class RestaurantData (
    val restaurantId : String?,
    val restaurantImage : String?,
    val floatingView : FloatingViewItem?,
    val featuredItemsList : List<FeaturedItem>?
) : Parcelable

@Parcelize
data class FloatingViewItem (
    val name : String?,
    val address : String?,
    val logo : String?,
    val deliveryTime : String?,
    val rating : String?
) : Parcelable


@Parcelize
data class FeaturedItem (
    val itemID : String?,
    val itemName : String?,
    val itemDesc : String?,
    val itemPrice : String?,
    val itemImage : String?
) : Parcelable