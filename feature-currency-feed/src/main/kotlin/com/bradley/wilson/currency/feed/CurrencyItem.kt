package com.bradley.wilson.currency.feed

data class Currency(
    val country: String,
    val rate: Double
)

data class CurrencyItem(
    val country: String,
    val rate: Double,
    val isBateRate: Boolean = false
)