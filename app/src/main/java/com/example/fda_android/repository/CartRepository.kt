package com.example.fda_android.repository

import com.example.fda_android.api.ApiInterface
import com.example.fda_android.data.CartResponse
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    suspend fun getCartData(token: String): CartResponse = apiInterface.viewCart(token).body()!!
}
