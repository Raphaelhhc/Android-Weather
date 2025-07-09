package com.example.weatherapp.data.mapper

import android.util.Log
import com.example.weatherapp.data.remote.DailyWeatherDataDto
import com.example.weatherapp.domain.model.DailyWeatherData

fun DailyWeatherDataDto.mapToDailyWeatherDataList(): List<DailyWeatherData>? {
    Log.d("BBB", "START")

    val dayList = this.dayList
    val maxTemperatureList = this.maxTemperatureList
    val minTemperatureList = this.minTemperatureList
    val precipitationList = this.precipitationList
    val weatherCodeList = this.weatherCodeList

    val isAllSizeSame = setOf(
        dayList.size,
        maxTemperatureList.size,
        minTemperatureList.size,
        precipitationList.size,
        weatherCodeList.size
    ).size == 1

    val isAnyEmpty = listOf(
        dayList,
        maxTemperatureList,
        minTemperatureList,
        precipitationList,
        weatherCodeList
    ).any { it.isEmpty() }

    if (!isAllSizeSame || isAnyEmpty) {
        return null
    }

    val length = dayList.size
    val dailyWeatherDataList = mutableListOf<DailyWeatherData>()
    for (index in 0 until length) {
        val data = DailyWeatherData(
            day = dayList[index],
            maxTemperature = maxTemperatureList[index],
            minTemperature = minTemperatureList[index],
            precipitation = precipitationList[index],
            weatherCode = weatherCodeList[index]
        )
        dailyWeatherDataList.add(data)
    }

    Log.d("BBB", dailyWeatherDataList.toString())
    return dailyWeatherDataList
}