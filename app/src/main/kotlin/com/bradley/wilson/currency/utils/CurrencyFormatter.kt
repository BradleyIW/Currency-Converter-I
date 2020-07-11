package com.bradley.wilson.currency.utils

import com.bradley.wilson.core.extensions.primitives.empty
import com.bradley.wilson.currency.feed.CurrencyItem
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.Currency

class CurrencyFormatter {

    private val numberInstance = NumberFormat.getNumberInstance()

    private val formatter by lazy {
        numberInstance.apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
            roundingMode = RoundingMode.HALF_UP
        }
    }

    fun currency(currencyCode: String): Currency? = currencyInstance(currencyCode)

    fun formatRateToCurrency(currencyItem: CurrencyItem): String {
        return formatter.format(currencyItem.rate)
    }

    fun formatCurrencyToRate(currencyText: String): BigDecimal {
        val format: DecimalFormat = numberInstance as DecimalFormat
        format.isParseBigDecimal = true

        val formattedCurrency = currencyText.replace(CURRENCY_DELIMITER_REGEX, String.empty())
        return try {
            format.parse(formattedCurrency) as BigDecimal
        } catch (parseException: ParseException) {
            BigDecimal(0.00)
        }
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
