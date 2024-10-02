package com.example.odes.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odes.core.data.source.Resource
import com.example.odes.core.domain.usecase.AuthUseCase
import com.example.odes.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _loginUiState = MutableSharedFlow<LoginUiState>()
    val loginUiState get() =  _loginUiState.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginUiState.emit(LoginUiState.Loading)
            authUseCase.login(email, password)
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _loginUiState.emit(LoginUiState.Success(it.data))
                        }
                        is Resource.Error -> {
                            _loginUiState.emit(LoginUiState.Error(it.message))
                        }
                        else -> Unit
                    }
                }
        }
    }

    fun storeEmail(email: String) {
        viewModelScope.launch {
            userUseCase.storeEmail(email)
        }
    }
}