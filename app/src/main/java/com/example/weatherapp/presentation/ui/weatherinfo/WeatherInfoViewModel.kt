package com.example.weatherapp.presentation.ui.weatherinfo

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.model.DailyWeatherData
import com.example.weatherapp.domain.model.HourlyWeatherData
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.resource.Resource
import com.example.weatherapp.presentation.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    var uiState by mutableStateOf(UiState())

    fun load() {
        viewModelScope.launch {

            uiState = uiState.copy(
                isLoading = true,
                error = null
            )

            val location = loadCurrentLocation()
            location?.let { loc ->
                val hourlyResult = loadHourlyWeather(loc.latitude, loc.longitude)
                val dailyResult = loadDailyWeather(loc.latitude, loc.longitude)
                if (hourlyResult is Resource.Error || dailyResult is Resource.Error) {
                    val message = hourlyResult.message + dailyResult.message
                    uiState = uiState.copy(
                        isLoading = false,
                        error = message
                    )
                } else {
                    uiState = uiState.copy(
                        location = loc,
                        currentWeather = getCurrentWeather(hourlyResult.data),
                        predictHourlyWeatherList = getPredictHourlyWeatherList(hourlyResult.data),
                        predictDailyWeatherList = dailyResult.data,
                        isLoading = false,
                        error = null
                    )
                }
            } ?: run {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location"
                )
            }
        }
    }

    private suspend fun loadCurrentLocation(): Location? {
        val location = withContext(Dispatchers.IO) {
            locationTracker.getCurrentLocation()
        }
        return location
    }

    private suspend fun loadHourlyWeather(lat: Double, lon: Double):
            Resource<List<HourlyWeatherData>> {
        return withContext(Dispatchers.IO) {
            weatherRepository.getHourlyWeatherList(
                lat,
                lon
            )
        }
    }

    private suspend fun loadDailyWeather(lat: Double, lon: Double):
            Resource<List<DailyWeatherData>> {
        return withContext(Dispatchers.IO) {
            weatherRepository.getDailyWeatherList(
                lat,
                lon
            )
        }
    }

    private fun getCurrentWeather(
        list: List<HourlyWeatherData>?
    ): HourlyWeatherData? {
        return list?.first()
    }

    private fun getPredictHourlyWeatherList(
        list: List<HourlyWeatherData>?
    ): List<HourlyWeatherData>? {
        return list?.subList(1, list.size)
    }

}