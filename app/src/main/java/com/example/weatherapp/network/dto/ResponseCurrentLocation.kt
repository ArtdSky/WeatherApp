package com.example.weatherapp.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseCurrentLocation(
	val latitude: String? = null,
	val longitude: String? = null,
	val	city : String? = null
)

