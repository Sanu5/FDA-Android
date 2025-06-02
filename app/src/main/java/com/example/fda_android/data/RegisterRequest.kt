package com.example.fda_android.data

import android.os.Parcelable
import com.example.fda_android.utils.UserType
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest(
    val name : String,
    val phoneNumber: String,
    val password: String,
    val role : UserType
) : Parcelable

