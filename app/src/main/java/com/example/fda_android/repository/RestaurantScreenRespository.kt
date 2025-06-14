package com.example.fda_android.repository

import com.example.fda_android.api.ApiInterface
import com.example.fda_android.data.RestaurantViewResponse
import javax.inject.Inject
import retrofit2.Response

class RestaurantScreenRepository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getRestaurantData(restaurantId: String): RestaurantViewResponse = apiInterface.getRestaurantData(restaurantId).body()!!
}
