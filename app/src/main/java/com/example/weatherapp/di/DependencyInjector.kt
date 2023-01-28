package com.example.weatherapp.di

import com.example.weatherapp.network.ApiService
import com.example.weatherapp.network.ApiServiceImpl
import com.example.weatherapp.viewmodel.MainViewModel
import io.ktor.client.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DependencyInjector = module {
    single<HttpClient> { HttpClient( ) }

    single<ApiService> { ApiServiceImpl( client = get() ) }

    viewModel { MainViewModel(get() ) }

    factory {ApiService.create() }
}