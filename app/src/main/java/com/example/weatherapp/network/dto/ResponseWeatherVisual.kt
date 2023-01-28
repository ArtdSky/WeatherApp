package com.example.weatherapp.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWeatherVisual(
	val days: List<DaysItem?>? = null
)

@Serializable
data class DaysItem(
	val temp: String? = null
)

