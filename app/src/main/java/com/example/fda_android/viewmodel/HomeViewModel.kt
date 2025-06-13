package com.example.fda_android.viewmodel

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
    private val _homeState = MutableStateFlow<UiState<HomeResponse>>(UiState.Loading)
    val homeState: StateFlow<UiState<HomeResponse>> = _homeState

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            _homeState.value = UiState.Loading
            try {
                val home = repository.getHomeScreenData()
                _homeState.value = UiState.Success(home)
            } catch (e: Exception) {
                _homeState.value = UiState.Error(message = e.localizedMessage?: "Unknown error occurred")
            }
        }
    }
}