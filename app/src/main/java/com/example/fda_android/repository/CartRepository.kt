package com.example.fda_android.repository

import com.example.fda_android.api.ApiInterface
import com.example.fda_android.data.CartResponse
import com.example.fda_android.data.CartUpdateRequest
import retrofit2.Response
import javax.inject.Inject

class CartRepository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getCartData(token: String): Response<CartResponse>  {
       return apiInterface.viewCart(token)
    }

    suspend fun updateItemQuantity(
        token: String,
        request: CartUpdateRequest
    ): Response<CartResponse> {
        return apiInterface.updateCartItem(
            cartUpdateRequest = request,
            token = token
        )
    }

}
