package com.example.fda_android.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fda_android.data.AuthResponse
import androidx.lifecycle.viewModelScope
import com.example.fda_android.data.LoginRequest
import com.example.fda_android.data.RegisterRequest
import com.example.fda_android.repository.AuthRepository
import com.example.fda_android.utils.TokenManager
import com.example.fda_android.utils.UiState
import com.example.fda_android.utils.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _loginResponse = MutableStateFlow<UiState<AuthResponse>>(UiState.Empty)
    val loginResponse: StateFlow<UiState<AuthResponse>> = _loginResponse

    private val _registerState = MutableStateFlow<UiState<AuthResponse>>(UiState.Empty)
    val registerState: StateFlow<UiState<AuthResponse>> = _registerState

    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            _loginResponse.value = UiState.Loading

            try {
                val request = LoginRequest(phoneNumber, password)
                val response = repository.loginUser(request)
                if(response.isSuccessful && response.body() != null) {
                    tokenManager.saveToken(response.body()!!.token)
                    _loginResponse.value = UiState.Success(response.body()!!)
                } else {
                    _loginResponse.value = UiState.Error(
                        code = response.code(),
                        message = response.errorBody()?.string()?: "Login error"
                    )
                }
            } catch (e: Exception) {
                _loginResponse.value = UiState.Error(message = e.localizedMessage?: "Unknown error")
            }
        }
    }

    fun register(name: String, phone: String, password: String) {
        viewModelScope.launch {
            _registerState.value = UiState.Loading
            try {
                val request = RegisterRequest(
                    name = name,
                    phone_no = phone,
                    password = password,
                    role = UserType.USER
                )
                val response = repository.registerUser(request)
                if (response.isSuccessful && response.body() != null) {
                    tokenManager.saveToken(response.body()!!.token)
                    _registerState.value = UiState.Success(response.body()!!)
                } else {
                    _registerState.value = UiState.Error(
                        code = response.code(),
                        message = response.message() ?: "Registration failed"
                    )
                }
            } catch (e: Exception) {
                _registerState.value = UiState.Error(message = e.localizedMessage ?: "An error occurred")
            }
        }
    }

}