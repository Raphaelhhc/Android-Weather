package com.example.weatherapp.presentation.ui.weatherinfo

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.model.DailyWeatherData
import com.example.weatherapp.domain.model.HourlyWeatherData
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    private val _currentWeather = MutableStateFlow<HourlyWeatherData?>(null)
    val currentWeather: StateFlow<HourlyWeatherData?> = _currentWeather

    private val _predictHourlyWeatherList = MutableStateFlow<List<HourlyWeatherData>?>(null)
    val predictHourlyWeatherList: StateFlow<List<HourlyWeatherData>?> = _predictHourlyWeatherList

    private val _predictDailyWeatherList = MutableStateFlow<List<DailyWeatherData>?>(null)
    val predictDailyWeatherList: StateFlow<List<DailyWeatherData>?> = _predictDailyWeatherList

    fun load() {
        viewModelScope.launch {
            val location = loadCurrentLocation()
            location?.let {
                loadHourlyWeather(location.latitude, location.longitude)
                loadDailyWeather(location.latitude, location.longitude)
            }
        }
    }

    private suspend fun loadCurrentLocation(): Location? {
        val location = locationTracker.getCurrentLocation()
        _location.value = location
        return location
    }

    private fun loadHourlyWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            val hourlyWeatherList = weatherRepository.getHourlyWeatherList(lat, lon)
            _currentWeather.value = getCurrentWeather(hourlyWeatherList)
            _predictHourlyWeatherList.value = getPredictHourlyWeatherList(hourlyWeatherList)
        }
    }

    private fun loadDailyWeather(lat: Double, lon: Double) {
        Log.d("AAA", "AAA")
        viewModelScope.launch {
            _predictDailyWeatherList.value = weatherRepository.getDailyWeatherList(lat, lon)
        }
    }

    private fun getCurrentWeather(
        list: List<HourlyWeatherData>?
    ): HourlyWeatherData? {
        return list?.first()
    }

    private fun getPredictHourlyWeatherList(
        list: List<HourlyWeatherData>?
    ): List<HourlyWeatherData>? {
        return list?.subList(1, list.size)
    }

}