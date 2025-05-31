package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class RegisterResponse(
    val success : Boolean
) : Parcelable
