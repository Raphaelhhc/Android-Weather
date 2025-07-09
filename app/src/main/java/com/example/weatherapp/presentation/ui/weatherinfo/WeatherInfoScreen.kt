package com.example.weatherapp.presentation.ui.weatherinfo

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController

@Composable
fun WeatherInfoScreen(
    vm: WeatherInfoViewModel,
    nv: NavHostController
) {
    val context = LocalContext.current

    val location by vm.location.collectAsState()
    val currentWeather by vm.currentWeather.collectAsState()
    val predictHourlyWeatherList by vm.predictHourlyWeatherList.collectAsState()
    val predictDailyWeatherList by vm.predictDailyWeatherList.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                    || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            vm.load()
        }
    }

    LaunchedEffect(Unit) {
        val hasAccessFineLocation =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocation =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (hasAccessFineLocation || hasAccessCoarseLocation) {
            vm.load()
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Current Location
        Text("Location", fontSize = 20.sp)
        if (location != null) {
            Text("Lat: ${location!!.latitude}, Lon: ${location!!.longitude}")
        } else {
            Text("Fetching location...")
        }

        // Current Weather
        Text("Current Weather", fontSize = 20.sp)
        if (currentWeather != null) {
            Text("Weather: ${currentWeather!!.weatherCode} " +
                    "Temp.: ${currentWeather!!.temperature} " +
                    "Rain poss.: ${currentWeather!!.precipitation}")
        } else {
            Text("Fetching current weather...")
        }

        // Hourly Weather Prediction next 24 hours
        Text("Hourly Weather", fontSize = 20.sp)
        if (predictHourlyWeatherList != null) {
            predictHourlyWeatherList!!.forEach { weather ->
                Text("Weather: ${weather.weatherCode} " +
                        "Temp.: ${weather.temperature} " +
                        "Rain poss.: ${weather.precipitation}")
            }
        } else {
            Text("Fetching hourly weather...")
        }

        // Daily Weather Prediction next 14 days
        Text("Daily Weather", fontSize = 20.sp)
        if (predictDailyWeatherList != null) {
            predictDailyWeatherList!!.forEach { weather ->
                Text("Weather: ${weather.weatherCode} " +
                        "Max Temp.: ${weather.maxTemperature} " +
                        "Main Temp.: ${weather.minTemperature} " +
                        "Rain poss.: ${weather.precipitation}")
            }
        } else {
            Text("Fetching daily weather...")
        }
    }

}