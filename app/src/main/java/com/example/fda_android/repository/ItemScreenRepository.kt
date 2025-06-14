package com.example.fda_android.repository

import com.example.fda_android.api.ApiInterface
import com.example.fda_android.data.ItemResponse
import retrofit2.Response
import javax.inject.Inject

class ItemScreenRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    suspend fun getItemData(
        restaurantId: String,
        itemId: String
    ): ItemResponse  = apiInterface.getItemData(restaurantId, itemId).body()!!
}
