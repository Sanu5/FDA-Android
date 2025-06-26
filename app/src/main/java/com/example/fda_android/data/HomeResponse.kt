package com.example.fda_android.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class HomeResponse (
    val data : HomeData,
    val success : Boolean
) : Parcelable


@Parcelize
data class HomeData (
    val couponView : List<CouponItem>,
    val restaurantList : List<RestaurantItem>
) : Parcelable

@Parcelize
data class CouponItem (
    val couponId : Int?,
    val discountAmount: String?,
    val description: String?,
    val logo: String?
) : Parcelable


@Parcelize
data class RestaurantItem (
    val id : Int?,
    val name : String?,
    val type : String?,
    val rating : Int?,
    val image : String?,
    val deliveryFee : String
) : Parcelable