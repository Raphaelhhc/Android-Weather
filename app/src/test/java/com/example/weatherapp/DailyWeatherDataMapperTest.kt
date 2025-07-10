package com.example.weatherapp

import com.example.weatherapp.data.mapper.mapToDailyWeatherDataList
import com.example.weatherapp.data.remote.DailyWeatherDataDto
import com.example.weatherapp.domain.model.DailyWeatherData
import org.junit.Assert.*
import org.junit.Test

class DailyWeatherDataMapperTest {

    @Test
    fun `map valid dto returns list`() {
        val dto = DailyWeatherDataDto(
            dayList = listOf("2024-01-01", "2024-01-02"),
            minTemperatureList = listOf(10.0, 12.0),
            maxTemperatureList = listOf(20.0, 22.0),
            precipitationList = listOf(0, 5),
            weatherCodeList = listOf(1, 2)
        )

        val result = dto.mapToDailyWeatherDataList()

        assertNotNull(result)
        assertEquals(
            listOf(
                DailyWeatherData(20.0, 10.0, 0, 1, "2024-01-01"),
                DailyWeatherData(22.0, 12.0, 5, 2, "2024-01-02")
            ),
            result
        )
    }

    @Test
    fun `map invalid dto with mismatched size returns null`() {
        val dto = DailyWeatherDataDto(
            dayList = listOf("2024-01-01", "2024-01-02"),
            minTemperatureList = listOf(10.0),
            maxTemperatureList = listOf(20.0),
            precipitationList = listOf(0, 5),
            weatherCodeList = listOf(1, 2)
        )

        val result = dto.mapToDailyWeatherDataList()

        assertNull(result)
    }
}
