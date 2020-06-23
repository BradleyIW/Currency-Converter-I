package com.bradley.wilson.currency.data.remote

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.mockito.eq
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify

class CurrencyRemoteDataSourceTest : UnitTest() {

    private lateinit var remoteDataSource: CurrencyRemoteDataSource

    @Mock
    private lateinit var currencyApiService: CurrencyApiService

    @Before
    fun setup() {
        remoteDataSource = CurrencyRemoteDataSource(currencyApiService)
    }

    @Test
    fun `given getCurrencyByBase is invoked with EUR base currency, then verify api service is requested with same base`() {
        runBlocking {
            remoteDataSource.latestCurrencyRates(EUR_BASE_CURRENCY)
            verify(currencyApiService).latestCurrencyRates(
                eq(
                    EUR_BASE_CURRENCY
                )
            )
        }
    }

    companion object {
        private const val EUR_BASE_CURRENCY = "EUR"
    }
}