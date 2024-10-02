package com.example.odes.core.data.source.remote.network

import RoutesResponse
import com.example.odes.core.data.source.remote.dto.request.RoutesRequest
import com.example.odes.core.data.source.remote.dto.response.PlacesResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface JekyService {

    companion object {
        const val KEY: String = "AIzaSyBu6mS62zBS9zSu4NFxKWcJZKqzgfh-8d0"
    }

    @Headers(
        "X-Goog-Api-Key: $KEY",
        "X-Goog-FieldMask: places.displayName,places.formattedAddress,places.priceLevel,places.location"
    )
    @POST("v1/places:searchText")
    suspend fun getPlaces(
        @Query("textQuery") textQuery: String
    ): PlacesResponse

    @Headers(
        "X-Goog-Api-Key: $KEY",
        "X-Goog-FieldMask: routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline"
    )
    @POST
    suspend fun getPlaceRoutes(
        @Url url: String,
        @Body request: RoutesRequest
    ): RoutesResponse

}