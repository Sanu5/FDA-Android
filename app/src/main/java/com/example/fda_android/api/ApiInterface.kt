package com.example.fda_android.api

import com.example.fda_android.data.AddCartItemRequest
import com.example.fda_android.data.CartResponse
import com.example.fda_android.data.CartUpdateRequest
import com.example.fda_android.data.HomeResponse
import com.example.fda_android.data.ItemResponse
import com.example.fda_android.data.LoginRequest
import com.example.fda_android.data.RegisterRequest
import com.example.fda_android.data.AuthResponse
import com.example.fda_android.data.BrowseScreenResponse
import com.example.fda_android.data.RestaurantViewResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {
    @POST("/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<AuthResponse>

    @GET("/home")
    suspend fun getHomeData(): Response<HomeResponse>

    @GET("/restaurants")
    suspend fun getRestaurantList(): Response<BrowseScreenResponse>

    @POST("/cart")
    suspend fun addCartItem(@Body addCartItemRequest: AddCartItemRequest): Response<CartResponse>

    @GET("/cart")
    suspend fun viewCart(): Response<CartResponse>

    @PUT("/cart")
    suspend fun updateCartItem(@Body cartUpdateRequest: CartUpdateRequest): Response<CartResponse>

    @GET("/restaurants/{id}")
    suspend fun getRestaurantData(@Path("id") restaurantId: String): Response<RestaurantViewResponse>

    @GET("/restaurants/{restaurantId}/menu/{itemId}")
    suspend fun getItemData(@Path("restaurantId") restaurantId: String?, @Path("itemId") itemId: String?): Response<ItemResponse>

}