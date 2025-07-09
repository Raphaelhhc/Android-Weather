package com.example.weatherapp.presentation.ui.weatherinfo

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController

@Composable
fun WeatherInfoScreen(
    vm: WeatherInfoViewModel,
    nv: NavHostController
) {
    val context = LocalContext.current

    val location by vm.location.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                    || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            vm.loadCurrentLocation()
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

        Log.d("WeatherInfoScreen", "$hasAccessFineLocation $hasAccessCoarseLocation")
        if (hasAccessFineLocation || hasAccessCoarseLocation) {
            vm.loadCurrentLocation()
        } else {
            Log.d("WeatherInfoScreen", "ELSE")
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    if (location != null) {
        Text("Lat: ${location!!.latitude}, Lon: ${location!!.longitude}")
    } else {
        Text("Fetching location...")
    }

}