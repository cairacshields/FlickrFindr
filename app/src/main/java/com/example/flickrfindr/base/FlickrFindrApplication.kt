package com.example.flickrfindr.base

import android.app.Application

class FlickrFindrApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinConfig.start(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        KoinConfig.stop()
    }
}