package com.example.odes.presentation.screen.picklocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odes.core.data.source.Resource
import com.example.odes.core.data.source.remote.dto.response.toDomain
import com.example.odes.core.domain.usecase.PlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickLocationViewModel @Inject constructor(private val placesUseCase: PlacesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<PickLocationUiState>(PickLocationUiState.Idle)
    val uiState: StateFlow<PickLocationUiState> get() = _uiState.asStateFlow()

    private var getPlacesJob: Job? = null

    fun getPlaces(keyword: String) {
        getPlacesJob?.cancel()
        getPlacesJob = viewModelScope.launch {
            _uiState.emit(PickLocationUiState.Loading)
            placesUseCase.getPlaces(keyword)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let {
                                _uiState.emit(PickLocationUiState.Success(it.toDomain()))
                            }
                        }
                        is Resource.Error -> {
                            _uiState.emit(PickLocationUiState.Error(result.message))
                        }
                        else -> Unit
                    }
                }
        }
    }
}