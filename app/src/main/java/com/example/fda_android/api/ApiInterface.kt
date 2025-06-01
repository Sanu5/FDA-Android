package com.example.fda_android.api

import com.example.fda_android.data.CartResponse
import com.example.fda_android.data.FeaturedItem
import com.example.fda_android.data.HomeResponse
import com.example.fda_android.data.ItemResponse
import com.example.fda_android.data.RegisterRequest
import com.example.fda_android.data.RegisterResponse
import com.example.fda_android.data.RestaurantItem
import com.example.fda_android.data.RestaurantViewResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {
    @POST("/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("/auth/login")
    suspend fun loginUser(@Body request: RegisterRequest): RegisterResponse

    @GET("/home")
    suspend fun getHomeData(): Response<HomeResponse>

    @POST("/cart/add")
    suspend fun addCartItem(
        @Body addCartItemRequest: AddCartItemRequest,
        @Header("Authorization") token: String
    ): Response<CartResponse>

    @GET("/cart")
    suspend fun viewCart(
        @Header("Authorization") token: String
    ): Response<CartResponse>

    @PUT("/cart")
    suspend fun updateCart(
        @Body cartUpdateRequest: CartUpdateRequest,
        @Header("Authorization") token: String
    ): Response<CartResponse>

    @POST("/orders")
    suspend fun placeOrder(): Response<Boolean>

//    @GET("/users")
//    suspend fun getUserProfile(
//        @Header("Authorization") token: String
//    ): Response<UserProfileView>
//
//    @PUT("/users")
//    suspend fun updateUserProfile(
//        @Header("Authorization") token: String,
//        @Body updateRequest: UpdateUserProfileRequest
//    ): Response<UpdateUserProfileResponse>
//
//    @POST("/users/reviews")
//    suspend fun giveReview(
//        @Header("Authorization") token: String,
//        @Body reviewRequest: ReviewRequest
//    ): Response<ReviewRequestResponse>

    @GET("/restaurants/{id}")
    suspend fun getRestaurantDetails(
        @Path("id") restaurantId: String
    ): Response<RestaurantViewResponse>

    @GET("/restaurants/{id}/menu/{itemId}")
    suspend fun getItemDetails(
        @Path("id") restaurantId: String,
        @Path("itemId") itemId: String
    ): Response<ItemResponse>



}