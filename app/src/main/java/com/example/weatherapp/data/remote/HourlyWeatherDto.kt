package com.example.weatherapp.data.remote

import com.squareup.moshi.Json

data class HourlyWeatherDto (

    @field:Json(name = "hourly")
    val hourlyWeatherDataDto: HourlyWeatherDataDto

)