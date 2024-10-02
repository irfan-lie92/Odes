package com.example.odes.presentation.screen.login

import com.example.odes.core.domain.model.User

sealed class LoginUiState {

    class Success(val data: User): LoginUiState()

    class Error(val message: String): LoginUiState()

    object Idle: LoginUiState()

    object Loading: LoginUiState()
}