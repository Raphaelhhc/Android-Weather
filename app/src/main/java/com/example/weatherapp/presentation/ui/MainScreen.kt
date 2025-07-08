package com.example.weatherapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.presentation.ui.citylist.CityListScreen
import com.example.weatherapp.presentation.ui.weatherinfo.WeatherInfoScreen
import com.example.weatherapp.presentation.ui.weatherinfo.WeatherInfoViewModel
import kotlinx.serialization.Serializable

@Composable
fun MainScreen(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    weatherInfoViewModel: WeatherInfoViewModel = hiltViewModel()
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = WeatherInfoScreen
    ) {
        composable<WeatherInfoScreen> {
            WeatherInfoScreen(
                vm = weatherInfoViewModel,
                nv = navController
            )
        }

        composable<CityListScreen> {
            CityListScreen()
        }
    }

}

@Serializable
object WeatherInfoScreen

@Serializable
object CityListScreen