package com.example.fda_android.api

import com.example.fda_android.data.CartResponse
import com.example.fda_android.data.HomeResponse
import com.example.fda_android.data.RegisterRequest
import com.example.fda_android.data.RegisterResponse
import com.example.fda_android.data.RestaurantItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiInterface {
    @POST("/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/auth/login")
    suspend fun loginUser(@Body request: RegisterRequest): RegisterResponse

    @GET("/home")
    suspend fun getHomeData(): Response<HomeResponse>

    @GET("/restaurants")
    suspend fun getRestaurants(): Response<List<RestaurantItem>>

    @POST("/cart")
    suspend fun addCartItem(): Response<Boolean>

    @GET("/cart")
    suspend fun viewCart(): Response<CartResponse>

    @PUT("/cart")
    suspend fun updateCart(): Response<Boolean>

    @POST("/orders")
    suspend fun placeOrder(): Response<Boolean>


}