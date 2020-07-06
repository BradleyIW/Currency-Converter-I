package com.bradley.wilson.currency.feed

import java.math.BigDecimal

data class Currency(
    val country: String,
    val rate: BigDecimal
)

data class CurrencyItem(
    val country: String,
    val rate: BigDecimal,
    val isBateRate: Boolean = false
)