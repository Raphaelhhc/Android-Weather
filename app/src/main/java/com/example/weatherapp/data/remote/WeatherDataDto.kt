package com.example.weatherapp.data.remote

import com.squareup.moshi.Json

data class WeatherDataDto (

    val time: List<String>,

    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,

    @field:Json(name = "precipitation_probability")
    val precipitations: List<Int>,

    @field:Json(name = "weather_code")
    val weatherCodes: List<Int>

)