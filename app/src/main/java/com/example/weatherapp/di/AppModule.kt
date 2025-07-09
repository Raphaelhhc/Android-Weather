package com.example.weatherapp.di

import android.app.Application
import com.example.weatherapp.data.location.LocationTrackerImpl
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {

        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()

    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        app: Application
    ): FusedLocationProviderClient {

        return LocationServices.getFusedLocationProviderClient(app)

    }

    @Provides
    @Singleton
    fun provideLocationTracker(
        app: Application,
        client: FusedLocationProviderClient
    ): LocationTracker {
        return LocationTrackerImpl(app, client)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi
    ): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }

}