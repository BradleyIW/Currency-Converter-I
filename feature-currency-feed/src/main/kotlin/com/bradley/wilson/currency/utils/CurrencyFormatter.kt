package com.bradley.wilson.currency.utils

import com.bradley.wilson.core.extensions.primitives.empty
import com.bradley.wilson.currency.feed.CurrencyItem
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class CurrencyFormatter {

    private val currencyFormatter by lazy {
        NumberFormat.getCurrencyInstance().apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
            roundingMode = RoundingMode.FLOOR
        }
    }

    fun currency(currencyCode: String): Currency? = currencyInstance(currencyCode)

    fun formatRateToCurrency(currencyItem: CurrencyItem): String {
        val currency = currency(currencyItem.country)
        currencyFormatter.currency = currency
        return currencyFormatter.format(currencyItem.rate)
            .replace(currency?.symbol ?: String.empty(), String.empty())
    }

    fun formatCurrencyToRate(currencyText: String): Double {
        val format = DecimalFormat.getNumberInstance()
        if (format is DecimalFormat) {
            format.isParseBigDecimal = true
        }
        val formattedCurrency = currencyText.replace(CURRENCY_DELIMITER_REGEX, String.empty())
        val decimal: BigDecimal = format.parse(formattedCurrency) as BigDecimal
        return decimal.toDouble()
    }

    private fun currencyInstance(countryCode: String) =
        try {
            Currency.getInstance(countryCode)
        } catch (exception: IllegalStateException) {
            null
        }

    companion object {
        private val CURRENCY_DELIMITER_REGEX = "[^\\d.,]".toRegex()
    }
}