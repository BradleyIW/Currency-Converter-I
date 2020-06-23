package com.bradley.wilson.currency.data.local

data class CurrencyEntity(
    val countryName: String,
    val currencyCode: String,
    val flag: String,
    val rate: Double
)
