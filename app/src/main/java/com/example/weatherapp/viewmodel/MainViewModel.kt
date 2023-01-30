package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.ApiService
import com.example.weatherapp.network.dto.DaysItem
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val service: ApiService
) : ViewModel() {
    init {
        getCurrentLocation()




    }

    data class ViewState(
        val city: String? = null,
        val lat: String? = null,
        val lon: String? = null,
        val temperature: String? = null
    )

    private val _viewState = MutableStateFlow(
        ViewState()
    )
    val viewState = _viewState.asStateFlow()


    fun getCurrentLocation() {
        viewModelScope.launch {
            val res = service.getCurrentLocations()
            _viewState.update { currentState ->
                currentState.copy(
                    city = res.city,
                    lat = res.latitude,
                    lon = res.longitude
                )
            }
        }
    }

    fun getWeatherApiTemp(lat: String, lon: String) {
        viewModelScope.launch {
            val res = service.getWeatherApiTemp(lat = lat, lon = lon)
            _viewState.update { currentState ->
                currentState.copy(
                    temperature = res.current?.temp_c.toString()
                )
            }
        }
    }

    fun getOpenWeatherTemp(lat: String, lon: String) {
        viewModelScope.launch {
            val res = service.getOpenWeatherTemp(lat = lat, lon = lon)
            _viewState.update { currentState ->
                currentState.copy(
                    temperature = res.main?.temp.toString()
                )
            }
        }
    }

    fun getWeatherVisualTemp(lat: String, lon: String) {
        viewModelScope.launch {
            val res = service.getWeatherVisualTemp(lat = lat, lon = lon)
//            val fahrenheitTemp = res.days?.last()?.temp
//            fahrenheitToCelsius(fahrenheitTemp)
            _viewState.update { currentState ->
                currentState.copy(
                    temperature = res.days?.last()?.temp
                )
            }
        }
    }

    private fun fahrenheitToCelsius(fahrenheit: String?): String {
        return ((fahrenheit?.toDouble()?.minus(32))?.times(0.55)).toString()
    }
}
