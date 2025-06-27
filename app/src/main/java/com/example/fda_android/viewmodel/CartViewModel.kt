package com.example.fda_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fda_android.data.CartResponse
import com.example.fda_android.data.CartUpdateRequest
import com.example.fda_android.repository.CartRepository
import com.example.fda_android.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {
    private val _cartState = MutableStateFlow<UiState<CartResponse>>(UiState.Empty)
    val cartState: StateFlow<UiState<CartResponse>> = _cartState

    fun fetchCartData() {
        viewModelScope.launch {
            _cartState.value = UiState.Loading
            try {
                val response = repository.getCartData()
                if (response.isSuccessful && response.body() != null) {
                    _cartState.value = UiState.Success(response.body()!!)
                } else {
                    _cartState.value = UiState.Error(
                        code = response.code(),
                        message = response.message() ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _cartState.value =
                    UiState.Error(message = e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }

    fun updateItemQuantity(itemId: String?, newQuantity: Int) {
        viewModelScope.launch {
            try {
                val updateRequest = CartUpdateRequest(
                    restaurantId = "current_restaurant_id",
                    itemId = itemId,
                    itemQuantity = newQuantity.toString()
                )

                val response = repository.updateItemQuantity(updateRequest)

                if (response.isSuccessful) {
                    fetchCartData()
                } else {
                    _cartState.value = UiState.Error(
                        message = "Update failed: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                _cartState.value = UiState.Error(
                    message = "Update error: ${e.localizedMessage}"
                )
            }
        }
    }
}

