package com.fasttrade.android.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fasttrade.android.di.TokenManager
import com.fasttrade.android.network.ApiService
import com.fasttrade.android.network.LoginRequest
import com.fasttrade.android.network.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val name: String) : AuthState()
    data class Error(val msg: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    fun login(email: String, password: String) = viewModelScope.launch {
        _state.value = AuthState.Loading
        runCatching { api.login(LoginRequest(email, password)) }
            .onSuccess { tokenManager.save(it.token); _state.value = AuthState.Success(it.name) }
            .onFailure { _state.value = AuthState.Error("Credenciais inválidas.") }
    }

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        _state.value = AuthState.Loading
        runCatching { api.register(RegisterRequest(name, email, password)) }
            .onSuccess { tokenManager.save(it.token); _state.value = AuthState.Success(it.name) }
            .onFailure { _state.value = AuthState.Error("Erro ao criar conta.") }
    }
}
