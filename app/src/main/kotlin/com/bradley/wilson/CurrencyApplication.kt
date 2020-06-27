package com.bradley.wilson

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import com.bradley.wilson.di.Injector

class CurrencyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.start(this)
        EmojiCompat.init(BundledEmojiCompatConfig(this))
    }
}
