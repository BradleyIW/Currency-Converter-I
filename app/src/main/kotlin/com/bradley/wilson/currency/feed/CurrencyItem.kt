package com.bradley.wilson.currency.feed

import java.math.BigDecimal

data class Currency(
    val country: String,
    val rate: BigDecimal,
    val lastUpdatedAt: Long
)

data class CurrencyItem(
    val country: String,
    val rate: BigDecimal,
    val isBateRate: Boolean = false,
    val lastUpdatedAt: Long
) {
    companion object {
        private const val DEFAULT_BASE_CURRENCY = "EUR"
        val EMPTY = CurrencyItem(DEFAULT_BASE_CURRENCY, BigDecimal.ONE, false, 0L)
    }
}
