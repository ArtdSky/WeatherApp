package com.example.weatherapp.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseOpenWeather(
	val main: Main? = null
)

@Serializable
data class Main(
	val temp: String? = null
)

