package com.example.weatherapp.data.remote

import com.squareup.moshi.Json

data class DailyWeatherDto (

    @field:Json(name = "daily")
    val dailyWeatherDataDto: DailyWeatherDataDto

)