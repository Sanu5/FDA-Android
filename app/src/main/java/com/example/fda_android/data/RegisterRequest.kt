package com.example.fda_android.data

import android.os.Parcelable
import com.example.fda_android.utils.UserType
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest(
    val success: Boolean,
    val data: Data?
) : Parcelable

@Parcelize
data class Data(
    val name : String,
    val phone_no: String,
    val password: String,
    val role : UserType?
) : Parcelable

