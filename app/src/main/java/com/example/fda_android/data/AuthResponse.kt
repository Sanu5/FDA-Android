package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthResponse(
    val success: Boolean?,
    val data: AuthData?
) : Parcelable

@Parcelize
data class AuthData(
    val token: String?
) : Parcelable
