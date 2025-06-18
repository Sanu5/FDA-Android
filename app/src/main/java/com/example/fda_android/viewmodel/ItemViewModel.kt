package com.example.fda_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fda_android.data.ItemResponse
import com.example.fda_android.repository.ItemScreenRepository
import com.example.fda_android.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ItemScreenRepository
) : ViewModel() {
    private val _itemState = MutableStateFlow<UiState<ItemResponse>>(UiState.Empty)
    val itemState: StateFlow<UiState<ItemResponse>> = _itemState


    fun fetchItemData(restaurantId: String, itemId: String) {
        viewModelScope.launch {
            _itemState.value = UiState.Loading
            try {
                val response = repository.getItemData(restaurantId, itemId)
                if (response.isSuccessful && response.body() != null) {
                    _itemState.value = UiState.Success(response.body()!!)
                } else {
                    _itemState.value = UiState.Error(
                        code = response.code(),
                        message = response.message() ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _itemState.value = UiState.Error(message = e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }
}
