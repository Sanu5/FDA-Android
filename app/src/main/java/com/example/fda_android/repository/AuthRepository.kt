package com.example.fda_android.repository

import com.example.fda_android.api.ApiInterface
import com.example.fda_android.data.AuthResponse
import com.example.fda_android.data.LoginRequest
import com.example.fda_android.data.RegisterRequest
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun loginUser(request: LoginRequest): Response<AuthResponse> {
        return apiInterface.loginUser(request)
    }

    suspend fun registerUser(request: RegisterRequest): Response<AuthResponse> {
        return apiInterface.registerUser(request)
    }
}