package com.example.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast?hourly=temperature_2m,precipitation_probability,weather_code&timezone=auto")
    suspend fun getHourlyWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double
    ): WeatherDto

}