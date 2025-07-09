package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.DailyWeatherData
import com.example.weatherapp.domain.model.HourlyWeatherData
import com.example.weatherapp.domain.resource.Resource

interface WeatherRepository {

    suspend fun getHourlyWeatherList(lat: Double, lon: Double):
            Resource<List<HourlyWeatherData>>

    suspend fun getDailyWeatherList(lat: Double, lon: Double):
            Resource<List<DailyWeatherData>>

}