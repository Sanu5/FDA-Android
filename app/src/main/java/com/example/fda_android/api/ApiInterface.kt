package com.example.fda_android.api

import com.example.fda_android.data.RegisterRequest
import com.example.fda_android.data.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @POST("/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/auth/login")
    suspend fun loginUser(@Body request: RegisterRequest): RegisterResponse


}