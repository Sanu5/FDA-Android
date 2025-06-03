package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


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
    val couponId : String?,
    val discountAmount: String?,
    val description: String?,
    val logo: Int?
) : Parcelable


@Parcelize
data class RestaurantItem (
    val id : String?,
    val name : String?,
    val type : String?,
    val rating : String?,
    val image : Int?,
    val deliveryFee : String
) : Parcelable