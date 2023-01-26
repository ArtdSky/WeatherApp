package com.example.weatherapp.di

import com.example.weatherapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DependencyInjector = module {
    viewModel { MainViewModel() }
    factory { }
}