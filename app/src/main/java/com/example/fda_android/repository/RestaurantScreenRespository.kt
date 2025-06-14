package com.example.fda_android.repository

import com.example.fda_android.api.ApiInterface
import com.example.fda_android.data.RestaurantViewResponse
import javax.inject.Inject
import retrofit2.Response

class RestaurantScreenRepository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getRestaurantData(restaurantId: String): RestaurantViewResponse {
        val response: Response<RestaurantViewResponse> = apiInterface.getRestaurantData(restaurantId)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Failed to fetch restaurant data: ${response.code()}")
        }
    }
}
