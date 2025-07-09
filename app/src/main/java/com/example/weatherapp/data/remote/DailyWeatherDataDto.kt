package com.example.weatherapp.data.remote

import com.squareup.moshi.Json

data class DailyWeatherDataDto (

    @field:Json(name = "time")
    val dayList: List<String>,

    @field:Json(name = "temperature_2m_min")
    val minTemperatureList: List<Double>,

    @field:Json(name = "temperature_2m_max")
    val maxTemperatureList: List<Double>,

    @field:Json(name = "precipitation_probability_max")
    val precipitationList: List<Int>,

    @field:Json(name = "weather_code")
    val weatherCodeList: List<Int>

)