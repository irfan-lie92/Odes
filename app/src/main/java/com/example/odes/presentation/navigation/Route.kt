package com.example.odes.presentation.navigation

sealed class Route(val route: String) {
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home")
    object PickLocationBottomSheet : Route("pick-location")
    object Error: Route("error")
}
