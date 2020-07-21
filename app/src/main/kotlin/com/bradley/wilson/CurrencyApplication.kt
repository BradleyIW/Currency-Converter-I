package com.bradley.wilson

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import com.bradley.wilson.core.connectivity.ConnectivityActivityLifecycleCallback
import com.bradley.wilson.core.di.Injector

class CurrencyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.start(this)
        EmojiCompat.init(BundledEmojiCompatConfig(this))
        registerActivityLifecycleCallbacks(ConnectivityActivityLifecycleCallback())
    }
}
