package com.bradley.wilson.currency.data.local

import java.math.BigDecimal

data class CurrencyRate(
    val countryCode: String,
    val value: BigDecimal
)
