package com.kt.startkit.di

import android.app.Application
import com.kt.startkit.core.logger.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StartKitApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Logger.init()
    }
}

