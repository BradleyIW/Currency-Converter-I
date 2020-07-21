package com.bradley.wilson.currency.feed.formatting

import com.bradley.wilson.core.extensions.primitives.empty
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale

class CurrencyFormatter(private val locale: Locale = CurrencyLocaleRetriever.getDefaultLocale()) {

    private val formatter by lazy {
        NumberFormat.getNumberInstance(locale).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
            roundingMode = RoundingMode.HALF_UP
        }
    }

    fun formatRateToCurrency(rate: BigDecimal): String = formatter.format(rate)

    fun formatCurrencyToRate(currencyText: String): BigDecimal {
        val format: DecimalFormat = formatter as DecimalFormat
        format.isParseBigDecimal = true

        val formattedCurrency = currencyText.replace(CURRENCY_DELIMITER_REGEX, String.empty())
        return try {
            format.parse(formattedCurrency) as BigDecimal
        } catch (parseException: ParseException) {
            BigDecimal.ZERO
        }
    }

    companion object {
        private val CURRENCY_DELIMITER_REGEX = "[^\\d.,]".toRegex()
    }
}
