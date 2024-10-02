package com.example.odes.presentation

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.odes.domain.model.EmptyStateModel
import com.example.odes.domain.model.PlacesModel
import com.example.odes.presentation.navigation.Route
import com.example.odes.presentation.screen.error.ErrorScreen
import com.example.odes.presentation.screen.home.HomeScreen
import com.example.odes.presentation.screen.login.LoginScreen
import com.example.odes.presentation.screen.picklocation.PLACE_BUNDLE
import com.example.odes.presentation.screen.picklocation.PickLocationBottomSheet
import com.example.odes.presentation.screen.register.RegisterScreen
import com.example.odes.presentation.theme.JekyTheme
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.net.URLEncoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { !viewModel.isSplashFinished.value }
        }
        setContent {
            JekyTheme {
                JekyApp(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
@Composable
fun JekyApp(viewModel: MainViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val bottomSheerNavigator = remember {BottomSheetNavigator(sheetState)}
    val navController = rememberNavController(bottomSheerNavigator)

    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheerNavigator
    ) {
       isUserLoggedIn?.let {isLoggedIn ->
           NavHost(navController = navController, startDestination = if (isLoggedIn) Route.Home.route else Route.Login.route) {
               composable(
                   route = Route.Login.route
               ) {
                   LoginScreen(
                       viewModel = hiltViewModel(),
                       onNavigateToHome = {
                           navController.navigate(Route.Home.route)
                       },
                       onNavigateToRegister = {
                           navController.navigate(Route.Register.route)
                       },
                       onLoginError = {
                           val json = Uri.encode(Gson().toJson(it))
                           navController.navigate("${Route.Error.route}/$json")
                       }
                   )
               }

               composable(
                   route = Route.Register.route
               ) {
                   RegisterScreen(
                       viewModel = hiltViewModel(),
                       onNavigateBack = {
                           navController.popBackStack()
                       },
                       onNavigateToHome = {
                           navController.navigate(Route.Home.route)
                       },
                       onRegisterError = {
                           val json = Uri.encode(Gson().toJson(it))
                           navController.navigate("${Route.Error.route}/$json")
                       }
                   )
               }

               composable(
                   route = Route.Home.route
               ) {
                   val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
                   HomeScreen(
                       viewModel = hiltViewModel(),
                       saveStateHandle = savedStateHandle,
                       onPickupClick = { origin, destination ->
                           navController.navigate(
                               "${Route.PickLocationBottomSheet.route}/true/${origin.encodeToUrl()}/${destination.encodeToUrl()}"
                           )
                       },
                       onDestinationClick = { origin, destination ->
                           navController.navigate(
                               "${Route.PickLocationBottomSheet.route}/false/${origin.encodeToUrl()}/${destination.encodeToUrl()}"
                           )
                       }
                   )
               }

               bottomSheet(
                   route = "${Route.PickLocationBottomSheet.route}/{isToGetPickupLocation}/{originLocation}/{destinationLocation}",
                   arguments = listOf(
                       navArgument("isToGetPickupLocation") { type = NavType.BoolType },
                       navArgument("originLocation") { type = NavType.StringType },
                       navArgument("destinationLocation") { type = NavType.StringType },
                   )
               ) { backStackEntry ->
                   val isToGetPickupLocation = backStackEntry.arguments?.getBoolean("isToGetPickupLocation") ?: true
                   val originLocation = backStackEntry.arguments?.getString("originLocation").orEmpty().decodeFromUrl()
                   val destinationLocation = backStackEntry.arguments?.getString("destinationLocation").orEmpty().decodeFromUrl()

                   PickLocationBottomSheet(
                       viewModel = hiltViewModel(),
                       if (originLocation == "-") "" else originLocation,
                       if (destinationLocation == "-") "" else destinationLocation,
                       isToGetPickupLocation,
                       onPlaceClick = { place, isPickupPlace ->
                           navController.previousBackStackEntry
                               ?.savedStateHandle
                               ?.set(PLACE_BUNDLE, PlacesModel(
                                   locationName = place.formattedAddress.orEmpty(),
                                   latitude = place.location?.latitude ?: 0.0,
                                   longitude = place.location?.longitude ?: 0.0,
                                   isPickupLocation = isPickupPlace
                               ))
                           navController.popBackStack()
                       },
                       onClose = {
                           navController.popBackStack()
                       }
                   )
               }

               bottomSheet(
                   route = "${Route.Error.route}/{empty-params}",
                   arguments = listOf(
                       navArgument("empty-params") { type = NavType.StringType }
                   )
               ) { backStackEntry ->
                   val emptyParams = backStackEntry.arguments?.getString("empty-params")
                   ErrorScreen(
                       Gson().fromJson(emptyParams, EmptyStateModel::class.java)
                   ) {
                       navController.popBackStack()
                   }
               }
           }
       }
    }
}
fun String.encodeToUrl(): String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.decodeFromUrl(): String {
    return URLDecoder.decode(this, "UTF-8")
}
