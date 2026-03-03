package com.margo.melichallenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * The base Application class for the Space Flight app.
 * Used primarily for initializing dependency injection (Hilt) and global tools like [Timber].
 */
@HiltAndroidApp
class SpaceFlightApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}