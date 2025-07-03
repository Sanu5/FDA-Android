package com.example.fda_android.repository

import com.example.fda_android.api.ApiInterface
import com.example.fda_android.data.BrowseScreenResponse
import com.example.fda_android.data.RestaurantItem
import retrofit2.Response
import javax.inject.Inject

class BrowseRepository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getRestaurantList(): Response<BrowseScreenResponse> {
        return apiInterface.getRestaurantList()
    }
}