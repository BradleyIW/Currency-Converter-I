package com.bradley.wilson.currency

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.currency.data.remote.responses.CurrencyResponse
import com.bradley.wilson.currency.usecase.Currency
import com.bradley.wilson.database.currency.rates.CurrencyRate
import com.bradley.wilson.database.currency.rates.RatesEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyMapperTest : UnitTest() {

    private lateinit var currencyMapper: CurrencyMapper

    @Before
    fun setup() {
        currencyMapper = CurrencyMapper()
    }

    @Test
    fun `given a currency response, when toCurrencyList is called, then map to rates to list of currency objects`() {
        val rates = mapOf(Pair(GBP_COUNTRY_CODE, GBP_CURRENCY_RATE))
        val currencyResponse = CurrencyResponse(TEST_BASE_EUR_CURRENCY, rates)
        val list = currencyMapper.toCurrencyList(currencyResponse)

        assertEquals(list.first().country, GBP_COUNTRY_CODE)
        assertEquals(list.first().rate, GBP_CURRENCY_RATE, 0.0)

    }

    @Test
    fun `given a rates entity, when toCurrencyList is called, then map to rates to list of currency objects`() {
        val rates = listOf(CurrencyRate(GBP_COUNTRY_CODE, GBP_CURRENCY_RATE))
        val ratesEntity = RatesEntity(TEST_BASE_EUR_CURRENCY, rates)
        val list = currencyMapper.toCurrencyList(ratesEntity)

        assertEquals(list.first().country, GBP_COUNTRY_CODE)
        assertEquals(list.first().rate, GBP_CURRENCY_RATE, 0.0)

    }

    @Test
    fun `given a base currency and list of Currency objects, when toRatesEntity is called, then map to RatesEntity`() {
        val rates = listOf(Currency(GBP_COUNTRY_CODE, GBP_CURRENCY_RATE))
        val ratesEntity = currencyMapper.toRatesEntity(TEST_BASE_EUR_CURRENCY, rates)

        assertEquals(ratesEntity.baseCurrency, TEST_BASE_EUR_CURRENCY)
        assertEquals(ratesEntity.rates.first().countryCode, GBP_COUNTRY_CODE)
        assertEquals(ratesEntity.rates.first().value, GBP_CURRENCY_RATE, 0.0)

    }

    @Test
    fun `given a base currency and list of Currency objects, when toCurrencyItem is called, then map to CurrencyItem`() {
        val currency = Currency(GBP_COUNTRY_CODE, GBP_CURRENCY_RATE)
        val ratesEntity = currencyMapper.toCurrencyItem(currency)

        assertEquals(ratesEntity.country, GBP_COUNTRY_CODE)
        assertEquals(ratesEntity.rate, GBP_CURRENCY_RATE, 0.0)

    }

    companion object {
        private const val TEST_BASE_EUR_CURRENCY = "EUR"
        private const val GBP_COUNTRY_CODE = "GBP"
        private const val GBP_CURRENCY_RATE = 1.1233452
    }
}

