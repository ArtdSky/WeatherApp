package com.example.weatherapp.network

import com.example.weatherapp.network.dto.ResponseWeatherApi
import com.example.weatherapp.network.dto.ResponseOpenWeather
import com.example.weatherapp.network.dto.ResponseWeatherVisual
import com.example.weatherapp.network.dto.ResponseCurrentLocation
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface ApiService {

    suspend fun getWeatherApiTemp(lat : String, lon : String): ResponseWeatherApi

    suspend fun getOpenWeatherTemp(lat : String, lon : String): ResponseOpenWeather

    suspend fun getWeatherVisualTemp(lat : String, lon : String): ResponseWeatherVisual

    suspend fun getCurrentLocations(): ResponseCurrentLocation

    companion object {
        fun create(): ApiService {
            return ApiServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.BODY
                    }
                    install(ContentNegotiation) {
                        json(Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        })
                    }
                }
            )
        }
    }
}