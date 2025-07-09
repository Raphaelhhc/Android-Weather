package com.example.weatherapp.data.remote

import com.squareup.moshi.Json

data class HourlyWeatherDataDto (

    @field:Json(name = "time")
    val timeList: List<String>,

    @field:Json(name = "temperature_2m")
    val temperatureList: List<Double>,

    @field:Json(name = "precipitation_probability")
    val precipitationList: List<Int>,

    @field:Json(name = "weather_code")
    val weatherCodeList: List<Int>

)