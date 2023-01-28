package com.example.weatherapp.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWeatherApi(
	val location: Location? = null,
	val current: Current? = null

)

@Serializable
data class Current(
	val temp_c: String? = null
)
@Serializable
data class Location(
	val name: String? = null
)



