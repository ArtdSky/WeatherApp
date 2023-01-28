package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.ApiService
import kotlinx.coroutines.launch

class MainViewModel(
    private val service: ApiService
)  : ViewModel(){
    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val res1 = service.getWeatherApiTemp(lat = "55.0",lon="73.4")
            Log.d("TAG-VM: res1", res1.toString() )

            val res2 = service.getOpenWeatherTemp(lat = "55.0",lon="73.4")
            Log.d("TAG-VM: res2", res2.toString() )

            val res3 = service.getWeatherVisualTemp(lat = "55.0",lon="73.4")
            Log.d("TAG-VM: res3", res3.toString() )

            val res4 = service.getCurrentLocations()
            Log.d("TAG-VM: res4", res4.toString() )
        }
    }
}
