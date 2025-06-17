package com.example.fda_android.repository

import com.example.fda_android.api.ApiInterface
import com.example.fda_android.data.CartResponse
import retrofit2.Response
import javax.inject.Inject

class CartRepository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getCartData(token: String): Response<CartResponse>  {
       return apiInterface.viewCart(token)
    }
}
