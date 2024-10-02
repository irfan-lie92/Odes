package com.example.odes.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odes.core.data.source.Resource
import com.example.odes.core.domain.model.User
import com.example.odes.core.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {

    private val _registerUiState = MutableSharedFlow<RegisterUiState>()
    val registerUiState get() = _registerUiState.asSharedFlow()

    fun register(user: User) {
        viewModelScope.launch {
            _registerUiState.emit(RegisterUiState.Loading)
            authUseCase.register(user)
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _registerUiState.emit(RegisterUiState.Success(it.data))
                        }
                        is Resource.Error -> {
                            _registerUiState.emit(RegisterUiState.Error(it.message))
                        }
                        else -> Unit
                    }
                }
        }
    }
}