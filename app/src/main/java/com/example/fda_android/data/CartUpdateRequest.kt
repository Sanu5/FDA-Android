package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartUpdateRequest(
    val success: Boolean,
    val data: CartUpdateData?
) : Parcelable

@Parcelize
data class CartUpdateData(
    val itemId: String?,
    val itemQuantity: Int?
) : Parcelable
