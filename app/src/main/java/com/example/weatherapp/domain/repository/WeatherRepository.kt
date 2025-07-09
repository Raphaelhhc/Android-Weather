package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.DailyWeatherData
import com.example.weatherapp.domain.model.HourlyWeatherData

interface WeatherRepository {

    suspend fun getHourlyWeatherList(lat: Double, lon: Double): List<HourlyWeatherData>?

    suspend fun getDailyWeatherList(lat: Double, lon: Double): List<DailyWeatherData>?

}