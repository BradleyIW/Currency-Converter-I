package com.bradley.wilson.currency

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.currency.data.remote.responses.CurrencyResponse
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyMapperTest : UnitTest() {

    private lateinit var currencyMapper: CurrencyMapper

    @Before
    fun setup() {
        currencyMapper = CurrencyMapper()
    }

    @Test
    fun `given a currency response, then map to rates to list of currency objects`() {
        val rates = mapOf(Pair(GBP_COUNTRY_CODE, GBP_CURRENCY_RATE))
        val currencyResponse = CurrencyResponse(TEST_BASE_EUR_CURRENCY, rates)
        val list = currencyMapper.toCurrencyList(currencyResponse)

        assertEquals(list.first().country, GBP_COUNTRY_CODE)
        assertEquals(list.first().rate, GBP_CURRENCY_RATE)

    }

    companion object {
        private const val TEST_BASE_EUR_CURRENCY = "EUR"
        private const val GBP_COUNTRY_CODE = "GBP"
        private const val GBP_CURRENCY_RATE = 1.1233452
    }
}