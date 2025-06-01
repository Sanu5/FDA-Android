package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartUpdateRequest(
    val restaurantId : String,
    val itemId : String,
    val itemQuantity : String
) : Parcelable
