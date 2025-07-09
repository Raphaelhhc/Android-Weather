package com.example.weatherapp.presentation.ui

import android.location.Location
import com.example.weatherapp.domain.model.DailyWeatherData
import com.example.weatherapp.domain.model.HourlyWeatherData

data class UiState (
    val location: Location? = null,
    val currentWeather: HourlyWeatherData? = null,
    val predictHourlyWeatherList: List<HourlyWeatherData>? = null,
    val predictDailyWeatherList: List<DailyWeatherData>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)