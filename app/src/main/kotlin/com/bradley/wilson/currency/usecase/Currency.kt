package com.bradley.wilson.currency.usecase

import java.math.BigDecimal

data class Currency(
    val country: String,
    val rate: BigDecimal,
    val lastUpdatedAt: Long
)
