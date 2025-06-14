package com.example.fda_android.utils

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val code: Int? = null, val message: String) : UiState<Nothing>()
}