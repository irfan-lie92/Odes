package com.example.odes.core.data.source.remote.dto.request

data class RoutesRequest(
    val origin: Origin? = null,
    val destination: Destination? = null
)

data class LatLng(
    val latitude: Any? = null,
    val longitude: Any? = null
)

data class Origin(
    val location: Location? = null
)

data class Location(
    val latLng: LatLng? = null
)

data class Destination(
    val location: Location? = null
)