package com.example.odes.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odes.core.data.source.Resource
import com.example.odes.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userUseCase: UserUseCase): ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn : StateFlow<Boolean?> get() = _isUserLoggedIn

    private val _isSplashFinished = MutableStateFlow(false)
    val isSplashFinished : StateFlow<Boolean> get() = _isSplashFinished

    init {
        getIsUserLoggedIn()
    }

    private fun getIsUserLoggedIn() {
        viewModelScope.launch {
            when(val result = userUseCase.isUserLogged().first()) {
                is Resource.Success -> {
                    _isUserLoggedIn.update {
                        result.data
                    }
                    _isSplashFinished.update {
                        true
                    }
                }
                is Resource.Error -> {
                    Log.d("getIsUserLoggedIn", "Error: ${result.message}")
                }
                else -> Unit
            }
        }
    }
}