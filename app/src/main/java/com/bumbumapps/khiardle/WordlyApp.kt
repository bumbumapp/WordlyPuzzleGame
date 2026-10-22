package com.bumbumapps.khiardle

import android.app.Application

class WordlyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        EdgeToEdgeCallbacks.register(this)
    }
}
