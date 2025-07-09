package com.example.weatherapp.data.repository

import com.example.weatherapp.data.mapper.mapToDailyWeatherDataList
import com.example.weatherapp.data.mapper.mapToHourlyWeatherDataList
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.model.DailyWeatherData
import com.example.weatherapp.domain.model.HourlyWeatherData
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.resource.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository {

    override suspend fun getHourlyWeatherList(
        lat: Double, lon: Double
    ): Resource<List<HourlyWeatherData>> {
        try {
            val hourlyWeatherDto = weatherApi.getHourlyWeatherData(lat, lon)
            return Resource.Success(
                data = hourlyWeatherDto.hourlyWeatherDataDto.mapToHourlyWeatherDataList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(
                message = e.message?: "Unknown error"
            )
        }
    }

    override suspend fun getDailyWeatherList(
        lat: Double, lon: Double
    ): Resource<List<DailyWeatherData>> {
        try {
            val dailyWeatherDto = weatherApi.getDailyWeatherData(lat, lon)
            return Resource.Success(
                data = dailyWeatherDto.dailyWeatherDataDto.mapToDailyWeatherDataList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(
                message = e.message?: "Unknown error"
            )
        }
    }
}