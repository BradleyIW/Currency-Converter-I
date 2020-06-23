package com.bradley.wilson.currency.data.local

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.functional.onSuccess
import com.bradley.wilson.database.currency.rates.CurrencyRate
import com.bradley.wilson.database.currency.rates.RatesDao
import com.bradley.wilson.database.currency.rates.RatesEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class CurrencyDatabaseServiceTest : UnitTest() {

    private lateinit var currencyDatabaseService: CurrencyDatabaseService

    @Mock
    private lateinit var ratesDao: RatesDao

    @Mock
    private lateinit var ratesEntity: RatesEntity

    @Before
    fun setup() {
        currencyDatabaseService = CurrencyDatabaseService(ratesDao)

        val currencyList = listOf(CurrencyRate(TEST_COUNTRY_CODE, TEST_RATE))
        `when`(ratesEntity.rates).thenReturn(currencyList)
    }

    @Test
    fun `given latestCurrencyRates are requested, when basedCurrency is EUR, then return latest rates from database`() {
        runBlocking {
            `when`(ratesDao.getLatestRatesFromBase(TEST_BASE_CURRENCY)).thenReturn(ratesEntity)

            val response = currencyDatabaseService.latestCurrencyRates(TEST_BASE_CURRENCY)

            verify(ratesDao).getLatestRatesFromBase(TEST_BASE_CURRENCY)

            response.onSuccess {
                assertEquals(it.rates.first().countryCode, TEST_COUNTRY_CODE)
                assertEquals(it.rates.first().value, TEST_RATE, 0.0)
            }
        }
    }

    @Test
    fun `given updateRates are requested, when basedCurrency is EUR, then return latest rates from database`() {
        runBlocking {
            currencyDatabaseService.updateRates(ratesEntity)

            verify(ratesDao).insertRate(ratesEntity)
        }
    }

    companion object {
        private const val TEST_COUNTRY_CODE = "USD"
        private const val TEST_RATE = 12.45775
        private const val TEST_BASE_CURRENCY = "EUR"
    }
}