package com.example.weatherapp

import com.example.weatherapp.data.remote.DailyWeatherDataDto
import com.example.weatherapp.data.remote.DailyWeatherDto
import com.example.weatherapp.data.remote.HourlyWeatherDataDto
import com.example.weatherapp.data.remote.HourlyWeatherDto
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.resource.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class WeatherRepositoryImplTest {

    private class FakeWeatherApi(
        private val hourly: HourlyWeatherDto? = null,
        private val daily: DailyWeatherDto? = null,
        private val throwError: Boolean = false
    ) : WeatherApi {
        override suspend fun getHourlyWeatherData(lat: Double, lon: Double): HourlyWeatherDto {
            if (throwError) throw RuntimeException("error")
            return hourly ?: throw RuntimeException("no data")
        }

        override suspend fun getDailyWeatherData(lat: Double, lon: Double): DailyWeatherDto {
            if (throwError) throw RuntimeException("error")
            return daily ?: throw RuntimeException("no data")
        }
    }

    @Test
    fun `repository returns success when api succeeds`() = runBlocking {
        val hourlyDto = HourlyWeatherDto(
            HourlyWeatherDataDto(
                timeList = listOf("t1"),
                temperatureList = listOf(10.0),
                precipitationList = listOf(0),
                weatherCodeList = listOf(1)
            )
        )
        val repo = WeatherRepositoryImpl(FakeWeatherApi(hourly = hourlyDto))

        val result = repo.getHourlyWeatherList(0.0, 0.0)

        assertTrue(result is Resource.Success)
        assertEquals(1, (result as Resource.Success).data?.size)
    }

    @Test
    fun `repository returns error when api throws`() = runBlocking {
        val repo = WeatherRepositoryImpl(FakeWeatherApi(throwError = true))

        val result = repo.getHourlyWeatherList(0.0, 0.0)

        assertTrue(result is Resource.Error)
    }
}
