package com.example.weatherapp

import com.example.weatherapp.data.mapper.mapToHourlyWeatherDataList
import com.example.weatherapp.data.remote.HourlyWeatherDataDto
import com.example.weatherapp.domain.model.HourlyWeatherData
import org.junit.Assert.*
import org.junit.Test

class HourlyWeatherDataMapperTest {

    @Test
    fun `map valid dto returns list`() {
        val dto = HourlyWeatherDataDto(
            timeList = listOf("t1", "t2"),
            temperatureList = listOf(18.0, 20.0),
            precipitationList = listOf(0, 10),
            weatherCodeList = listOf(1, 2)
        )

        val result = dto.mapToHourlyWeatherDataList()

        assertNotNull(result)
        assertEquals(
            listOf(
                HourlyWeatherData(18.0, 0, 1, "t1"),
                HourlyWeatherData(20.0, 10, 2, "t2")
            ),
            result
        )
    }

    @Test
    fun `map invalid dto with mismatched size returns null`() {
        val dto = HourlyWeatherDataDto(
            timeList = listOf("t1"),
            temperatureList = listOf(18.0, 20.0),
            precipitationList = listOf(0),
            weatherCodeList = listOf(1)
        )

        val result = dto.mapToHourlyWeatherDataList()

        assertNull(result)
    }
}
