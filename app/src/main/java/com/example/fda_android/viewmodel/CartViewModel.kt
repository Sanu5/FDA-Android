package com.example.fda_android.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fda_android.data.AddCartItemRequest
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
                    itemId = itemId,
                    itemQuantity = newQuantity
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

    fun addCartItem(restaurantId: String?, itemId: String?, itemQuantity: Int?) {
        viewModelScope.launch {
            try {
                val addRequest = AddCartItemRequest(restaurantId = restaurantId, itemId = itemId, itemQuantity = itemQuantity)
                Log.d("CartViewModel", "Request: $addRequest")

                val response = repository.addCartItem(addRequest)
                Log.d("CartViewModel", "Response: $response")
                Log.d("CartViewModel", "Response Body: ${response.body()}")

                if (response.isSuccessful && response.body() != null) {
                    _cartState.value = UiState.Success(response.body()!!)
                } else {
                    _cartState.value = UiState.Error(
                        code = response.code(),
                        message = response.message() ?: "Unknown error occurred"
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

