package com.example.weatherapp.domain.model

data class HourlyWeatherData(
    val temperature: Double,
    val precipitation: Int,
    val weatherCode: Int,
    val time: String
)
