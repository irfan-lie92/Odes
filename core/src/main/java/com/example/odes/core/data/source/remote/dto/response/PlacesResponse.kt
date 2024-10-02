package com.example.odes.core.data.source.remote.dto.response

import com.example.odes.core.domain.model.DisplayName
import com.example.odes.core.domain.model.Location
import com.example.odes.core.domain.model.Places
import com.example.odes.core.domain.model.PlacesItem
import com.google.gson.annotations.SerializedName

data class PlacesResponse(

    @field:SerializedName("places")
    val places: List<PlacesItemResponse>
)

data class LocationResponse(

    @field:SerializedName("latitude")
    val latitude: Double,

    @field:SerializedName("longitude")
    val longitude: Double
)

data class DisplayNameResponse(

    @field:SerializedName("text")
    val text: String,

    @field:SerializedName("languageCode")
    val languageCode: String
)

data class PlacesItemResponse(

    @field:SerializedName("formattedAddress")
    val formattedAddress: String,

    @field:SerializedName("displayName")
    val displayName: DisplayNameResponse,

    @field:SerializedName("location")
    val location: LocationResponse
)


fun PlacesResponse.toDomain() = Places(
    this.places.map {
        PlacesItem(
            it.formattedAddress,
            DisplayName(
                it.displayName.text,
                it.displayName.languageCode
            ),
            Location(
                it.location.latitude,
                it.location.longitude
            )
        )
    }
)