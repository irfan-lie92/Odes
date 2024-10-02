package com.example.odes.core.domain.model

data class Places(
    val places: List<PlacesItem>
)

data class Location(
    val latitude: Double,
    val longitude: Double
)

data class DisplayName(
    val text: String?,
    val languageCode: String?
)

data class PlacesItem(
    val formattedAddress: String?,
    val displayName: DisplayName?,
    val location: Location?
)