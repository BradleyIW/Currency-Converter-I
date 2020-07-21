package com.bradley.wilson.currency.feed.formatting

import com.bradley.wilson.testing.UnitTest
import com.bradley.wilson.core.extensions.math.equalTo
import com.bradley.wilson.core.extensions.primitives.empty
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.math.BigDecimal
import java.util.Locale

class CurrencyFormatterTest : UnitTest() {

    private lateinit var currencyFormatter: CurrencyFormatter

    @Test
    fun `given a German device, when formatRateToCurrency is called, then assert delimiters are correct to two decimal places`() {
        currencyFormatter = CurrencyFormatter(Locale.GERMAN)

        val input = BigDecimal(TWO_HUNDRED_THOUSAND)
        val output = currencyFormatter.formatRateToCurrency(input)

        assertEquals(output, GERMAN_FORMATTED_NUMBER)
    }

    @Test
    fun `given an English device, when formatRateToCurrency is called, hen assert delimiters are correct to two decimal places`() {
        currencyFormatter = CurrencyFormatter(Locale.UK)

        val input = BigDecimal(TWO_HUNDRED_THOUSAND)
        val output = currencyFormatter.formatRateToCurrency(input)

        assertEquals(output, UK_FORMATTED_NUMBER)
    }

    @Test
    fun `given a German device, when formatCurrencyToRate is called and input is valid, then assert delimiters are correct to two decimal places`() {
        currencyFormatter = CurrencyFormatter(Locale.GERMAN)

        val output = currencyFormatter.formatCurrencyToRate(GERMAN_FORMATTED_NUMBER)

        assertEquals(output.intValueExact(), TWO_HUNDRED_THOUSAND)
    }

    @Test
    fun `given an English device, when formatCurrencyToRate is called and input is valid, hen assert delimiters are correct to two decimal places`() {
        currencyFormatter = CurrencyFormatter(Locale.UK)

        val output = currencyFormatter.formatCurrencyToRate(UK_FORMATTED_NUMBER)

        assertEquals(output.intValueExact(), TWO_HUNDRED_THOUSAND)
    }

    @Test
    fun `given an invalid input, when formatCurrencyToRate is called, then assert rate is ZERO`() {
        currencyFormatter = CurrencyFormatter()

        val output = currencyFormatter.formatCurrencyToRate(String.empty())

        assertTrue(output.equalTo(BigDecimal.ZERO))
    }

    companion object {
        private const val TWO_HUNDRED_THOUSAND = 200000
        private const val GERMAN_FORMATTED_NUMBER = "200.000,00"
        private const val UK_FORMATTED_NUMBER = "200,000.00"
    }
}
