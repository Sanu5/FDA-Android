package com.example.fda_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fda_android.data.RestaurantViewResponse
import com.example.fda_android.repository.RestaurantScreenRepository
import com.example.fda_android.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantScreenRepository
) : ViewModel() {
    private val _restaurantState = MutableStateFlow<UiState<RestaurantViewResponse>>(UiState.Empty)
    val restaurantState: StateFlow<UiState<RestaurantViewResponse>> = _restaurantState

    fun fetchRestaurantData(restaurantId: String) {
        viewModelScope.launch {
            _restaurantState.value = UiState.Loading
            try {
                val response = repository.getRestaurantData(restaurantId)
                if (response.isSuccessful && response.body() != null) {
                    _restaurantState.value = UiState.Success(response.body()!!)
                } else {
                    _restaurantState.value = UiState.Error(
                        code = response.code(),
                        message = response.message() ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _restaurantState.value = UiState.Error(message = e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }
}
