package com.example.fda_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fda_android.data.BrowseScreenResponse
import com.example.fda_android.repository.BrowseRepository
import com.example.fda_android.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(private val repository: BrowseRepository) : ViewModel() {
    private val _resState: MutableStateFlow<UiState<BrowseScreenResponse>> = MutableStateFlow(UiState.Empty)
    val resState: StateFlow<UiState<BrowseScreenResponse>> = _resState

    fun fetchRestaurantList() {
        viewModelScope.launch {
            _resState.value = UiState.Loading
            try {
                val response = repository.getRestaurantList()
                if (response.isSuccessful && response.body() != null) {
                    _resState.value = UiState.Success(response.body()!!)
                } else {
                    _resState.value = UiState.Error(
                        code = response.code(),
                        message = response.message() ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _resState.value = UiState.Error(message = e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }
}