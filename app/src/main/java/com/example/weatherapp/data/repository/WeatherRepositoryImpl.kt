package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.mapper.mapToDailyWeatherDataList
import com.example.weatherapp.data.mapper.mapToHourlyWeatherDataList
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.model.DailyWeatherData
import com.example.weatherapp.domain.model.HourlyWeatherData
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository {

    override suspend fun getHourlyWeatherList(
        lat: Double, lon: Double
    ): List<HourlyWeatherData>? {
        val hourlyWeatherDto = weatherApi.getHourlyWeatherData(lat, lon)
        return hourlyWeatherDto.hourlyWeatherDataDto.mapToHourlyWeatherDataList()
    }

    override suspend fun getDailyWeatherList(
        lat: Double, lon: Double
    ): List<DailyWeatherData>? {
        val dailyWeatherDto = weatherApi.getDailyWeatherData(lat, lon)
        return dailyWeatherDto.dailyWeatherDataDto.mapToDailyWeatherDataList()
    }
}