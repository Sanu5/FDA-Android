package com.example.fda_android.repository

import com.example.fda_android.api.ApiInterface
import com.example.fda_android.data.HomeResponse
import retrofit2.Response
import javax.inject.Inject

class HomeScreenRepository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getHomeScreenData(): Response<HomeResponse> {
       return apiInterface.getHomeData()
    }
}