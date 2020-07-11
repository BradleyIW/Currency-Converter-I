package com.bradley.wilson.core.database.currency.rates

import java.math.BigDecimal

data class CurrencyRate(
    val countryCode: String,
    val value: BigDecimal
)
