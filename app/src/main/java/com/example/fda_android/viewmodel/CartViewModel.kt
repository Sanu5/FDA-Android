package com.example.fda_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fda_android.data.CartResponse
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
    private val _cartState = MutableStateFlow<UiState<CartResponse>>(UiState.Loading)
    val cartState: StateFlow<UiState<CartResponse>> = _cartState

    fun fetchCartData(token: String) {
        viewModelScope.launch {
            _cartState.value = UiState.Loading
            try {
                val cart = repository.getCartData(token)
                _cartState.value = UiState.Success(cart)
            } catch (e: Exception) {
                _cartState.value = UiState.Error(message = e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }
}
