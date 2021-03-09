package com.jarvizu.geotopic.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GeoTopicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : MyTree() {

        })
    }
}