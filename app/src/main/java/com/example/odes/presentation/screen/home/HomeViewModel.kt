package com.example.odes.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odes.core.data.source.Resource
import com.example.odes.core.data.source.remote.dto.request.LatLng
import com.example.odes.core.domain.usecase.PlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val placesUseCase: PlacesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> get() = _uiState.asStateFlow()

    fun getPlaceRoutes(origin: Pair<Double, Double>, destination: Pair<Double, Double>) {
        viewModelScope.launch {
            _uiState.emit(HomeUiState.Loading)
            placesUseCase.getPlaceRoutes(
                origin = LatLng(origin.first, origin.second),
                destination = LatLng(destination.first, destination.second)
            ).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _uiState.emit(HomeUiState.Success(result.data))
                    }
                    is Resource.Error -> {
                        _uiState.emit(HomeUiState.Error(result.message))
                    }
                    else -> Unit
                }
            }
        }
    }
}