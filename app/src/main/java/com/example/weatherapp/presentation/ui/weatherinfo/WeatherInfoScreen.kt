package com.example.weatherapp.presentation.ui.weatherinfo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun WeatherInfoScreen(
    vm: WeatherInfoViewModel,
    nv: NavHostController
) {
    Text("WeatherInfoScreen")
}