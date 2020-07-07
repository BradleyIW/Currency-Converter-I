package com.bradley.wilson.currency.data.remote

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.mockito.eq
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify

class CurrencyApiServiceTest : UnitTest() {

    private lateinit var currencyApiService: CurrencyApiService

    @Mock
    private lateinit var api: CurrencyApi

    @Before
    fun setup() {
        currencyApiService = CurrencyApiService(api)
    }

    @Test
    fun `given EUR currency is passed into getCurrenciesForBase, then verify api is requested with same base currency`() {
        runBlocking {
            currencyApiService.latestCurrencyRates(EURO_BASE_CURRENCY)
            verify(api).latestCurrencyRates(eq(EURO_BASE_CURRENCY))
        }
    }

    companion object {
        private const val EURO_BASE_CURRENCY = "EUR"
    }
}
