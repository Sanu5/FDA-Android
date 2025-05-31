package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest(
    val phoneNumber: String,
    val password: String
) : Parcelable

