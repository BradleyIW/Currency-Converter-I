package com.bradley.wilson.currency.usecase

data class Currency(
    val country: String,
    val rate: Double
)

data class CurrencyItem(
    val country: String,
    val rate: Double
)