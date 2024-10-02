package com.example.odes.core.domain.repository

import RoutesResponse
import com.example.odes.core.data.source.Resource
import com.example.odes.core.data.source.remote.dto.request.LatLng
import com.example.odes.core.data.source.remote.dto.response.PlacesResponse
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {

    suspend fun getPlaces(keyword: String) : Flow<Resource<PlacesResponse>>
    suspend fun getPlacesRoute(origin: LatLng, destination: LatLng): Flow<Resource<RoutesResponse>>
}