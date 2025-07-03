package com.example.fda_android.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class BrowseScreenResponse(
    val success: Boolean,
    val data: List<RestaurantItem>
): Parcelable