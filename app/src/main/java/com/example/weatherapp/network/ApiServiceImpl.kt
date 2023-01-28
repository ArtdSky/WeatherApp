package com.example.weatherapp.network

import com.example.weatherapp.network.dto.ResponseWeatherApi
import com.example.weatherapp.network.dto.ResponseOpenWeather
import com.example.weatherapp.network.dto.ResponseWeatherVisual
import com.example.weatherapp.network.dto.ResponseCurrentLocation
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    override suspend fun getWeatherApiTemp(lat : String, lon : String): ResponseWeatherApi {
        return client.get(HttpRoutes.WEATHER_API){
            url {
                parameters.append("key", "a417b27b7c0746ceab5124157232501")
                parameters.append("q", "$lat,$lon")
            }
        }.body()
    }

    override suspend fun getOpenWeatherTemp(lat : String, lon : String): ResponseOpenWeather {
        return client.get(HttpRoutes.OPENWEATHER_API){
            url {
                parameters.append("lat", lat)
                parameters.append("lon", lon)
                parameters.append("appid", "1a1aaae6a5881fb09024b0f1e1621e6e")
                parameters.append("units", "metric")
            }
        }.body()
    }

    override suspend fun getWeatherVisualTemp(lat: String, lon: String): ResponseWeatherVisual {
        return client.get(HttpRoutes.WEATHERVISUAL_API){
            url {
                appendPathSegments("timeline", "$lat,$lon")
                parameters.append("key", "NVB8CAXSGKZAQZTKEDDTXJPLC")

            }
        }.body()
    }

    override suspend fun getCurrentLocations(): ResponseCurrentLocation {
        return client.get { url(HttpRoutes.IPWHO) }.body()
    }


}