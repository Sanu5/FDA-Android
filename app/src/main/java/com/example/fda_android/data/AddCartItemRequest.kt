package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddCartItemRequest(
    val restaurantId : String,
    val itemId : String,
    val itemName : String,
    val itemImage : String,
    val itemQuantity : String,
    val itemPrice : String
) : Parcelable
