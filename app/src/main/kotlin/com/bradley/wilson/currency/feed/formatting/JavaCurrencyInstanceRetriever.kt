package com.bradley.wilson.currency.feed.formatting

import java.util.Currency

object JavaCurrencyInstanceRetriever {
    fun currency(countryCode: String) =
        try {
            Currency.getInstance(countryCode)
        } catch (exception: IllegalStateException) {
            null
        }
}
