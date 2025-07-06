package com.example.fda_android.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fda_android.data.HomeResponse
import com.example.fda_android.repository.HomeScreenRepository
import com.example.fda_android.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeScreenRepository): ViewModel() {
    private val _homeState = MutableStateFlow<UiState<HomeResponse>>(UiState.Empty)
    val homeState: StateFlow<UiState<HomeResponse>> = _homeState

    init {
        fetchHomeData()
    }

    fun fetchHomeData() {
        viewModelScope.launch {
            _homeState.value = UiState.Loading
            try {
                val response = repository.getHomeScreenData()
                Log.d("HomeViewModel", "Response: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _homeState.value = UiState.Success(response.body()!!)
                } else {
                    _homeState.value = UiState.Error(
                        code = response.code(),
                        message = response.message() ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _homeState.value = UiState.Error(message = e.localizedMessage?: "Unknown error occurred")
            }
        }
    }
}