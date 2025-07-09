package com.example.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?hourly=temperature_2m,weather_code,precipitation_probability&timezone=auto&forecast_days=2")
    suspend fun getHourlyWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double
    ): HourlyWeatherDto

    @GET("v1/forecast?daily=weather_code,temperature_2m_min,temperature_2m_max,precipitation_probability_max&timezone=auto&forecast_days=14")
    suspend fun getDailyWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double
    ): DailyWeatherDto

}