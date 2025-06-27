package com.example.fda_android.repository

import com.example.fda_android.api.ApiInterface
import com.example.fda_android.data.CartResponse
import com.example.fda_android.data.CartUpdateRequest
import retrofit2.Response
import javax.inject.Inject

class CartRepository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getCartData(): Response<CartResponse>  {
       return apiInterface.viewCart()
    }

    suspend fun updateItemQuantity(request: CartUpdateRequest): Response<CartResponse> {
        return apiInterface.updateCartItem(cartUpdateRequest = request)
    }
}
