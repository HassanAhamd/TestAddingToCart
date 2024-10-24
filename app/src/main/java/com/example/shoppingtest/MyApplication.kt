package com.example.shoppingtest

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication: Application(){
    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        // It will show all logs and toast if true ByDefault is true make sure to set it false before you make a release build
       // Helpers.isDebugging = true
        appContext = this
    }
}