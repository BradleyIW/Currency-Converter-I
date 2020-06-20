package com.bradley.wilson

import android.app.Application
import com.bradley.wilson.di.Injector

class CurrencyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.start(this)
    }
}