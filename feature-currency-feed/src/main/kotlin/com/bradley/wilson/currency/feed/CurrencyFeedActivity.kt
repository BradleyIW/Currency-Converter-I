package com.bradley.wilson.currency.feed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bradley.wilson.core.extensions.android.replaceFragment
import com.bradley.wilson.currency.R

class CurrencyFeedActivity : AppCompatActivity(R.layout.activity_currency_feed) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState ?: replaceFragment(
            R.id.currency_feed_layout_container,
            CurrencyFeedFragment.newInstance(),
            false
        )
    }
}

