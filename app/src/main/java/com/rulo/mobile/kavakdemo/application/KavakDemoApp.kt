package com.rulo.mobile.kavakdemo.application

import android.app.Application
import com.rulo.mobile.kavakdemo.BuildConfig
import timber.log.Timber

class KavakDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // ToDo Change to crashlitics or another
            Timber.plant(Timber.DebugTree())
        }
    }
}