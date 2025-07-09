package com.example.weatherapp.domain.model

data class DailyWeatherData(
    val maxTemperature: Double,
    val minTemperature: Double,
    val precipitation: Int,
    val weatherCode: Int,
    val day: String
)
