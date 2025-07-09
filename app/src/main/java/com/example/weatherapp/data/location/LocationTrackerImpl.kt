package com.example.weatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.weatherapp.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationTrackerImpl(
    private val application: Application,
    private val client: FusedLocationProviderClient
) : LocationTracker {

    override suspend fun getCurrentLocation(): Location? {

        val hasAccessFineLocation =
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocation =
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE)
                    as LocationManager

        val isGpsEnabled =
            locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            ) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )

        if (!hasAccessFineLocation && !hasAccessCoarseLocation || !isGpsEnabled)
            return null

        return suspendCancellableCoroutine { continuation ->

            client.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { location ->
                continuation.resume(location)
            }.addOnFailureListener {
                continuation.resume(null)
            }.addOnCanceledListener {
                continuation.cancel()
            }

        }

    }
}