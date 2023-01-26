package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.di.DependencyInjector
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(DependencyInjector))
        }
    }
}