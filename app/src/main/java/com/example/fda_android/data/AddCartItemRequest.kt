package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class  AddCartItemRequest(
    val success: Boolean,
    val data: AddCartData?
) : Parcelable

@Parcelize
data class AddCartData(
    val restaurantId: String?,
    val itemId: String?,
    val itemQuantity: Int?,
) : Parcelable
