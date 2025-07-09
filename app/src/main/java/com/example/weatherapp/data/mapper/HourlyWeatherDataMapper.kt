package com.example.weatherapp.data.mapper

import android.util.Log
import com.example.weatherapp.data.remote.HourlyWeatherDataDto
import com.example.weatherapp.domain.model.HourlyWeatherData

fun HourlyWeatherDataDto.mapToHourlyWeatherDataList(): List<HourlyWeatherData>? {

    val timeList = this.timeList
    val temperatureList = this.temperatureList
    val precipitationList = this.precipitationList
    val weatherCodeList = this.weatherCodeList

    val isAllSizeSame = setOf(
        timeList.size,
        temperatureList.size,
        precipitationList.size,
        weatherCodeList.size
    ).size == 1

    val isAnyEmpty = listOf(
        timeList,
        temperatureList,
        precipitationList,
        weatherCodeList
    ).any { it.isEmpty() }

    if (!isAllSizeSame || isAnyEmpty) {
        return null
    }

    val length = timeList.size
    val hourlyWeatherDataList = mutableListOf<HourlyWeatherData>()
    for (index in 0 until length) {
        val data = HourlyWeatherData(
            time = timeList[index],
            temperature = temperatureList[index],
            precipitation = precipitationList[index],
            weatherCode = weatherCodeList[index]
        )
        hourlyWeatherDataList.add(data)
    }

    return hourlyWeatherDataList
}