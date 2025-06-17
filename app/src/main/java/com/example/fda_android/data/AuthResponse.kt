package com.example.fda_android.data

import android.os.Parcelable
import com.example.fda_android.utils.UserType
import kotlinx.parcelize.Parcelize

data class AuthResponse(
    val success : Boolean,
    val token: String,
    val user: User
)

@Parcelize
data class User(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val role: UserType
) : Parcelable
