package com.bradley.wilson.currency.data.local

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.functional.onSuccess
import com.bradley.wilson.currency.data.local.source.CurrencyDatabaseService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.math.BigDecimal

class CurrencyDatabaseServiceTest : UnitTest() {

    private lateinit var currencyDatabaseService: CurrencyDatabaseService

    @Mock
    private lateinit var ratesDao: RatesDao

    @Mock
    private lateinit var currencyEntity: CurrencyEntity

    @Before
    fun setup() {
        currencyDatabaseService = CurrencyDatabaseService(ratesDao)

        val currencyList = listOf(CurrencyRate(TEST_COUNTRY_CODE, TEST_RATE))
        `when`(currencyEntity.rates).thenReturn(currencyList)
    }

    @Test
    fun `given latestCurrencyRates are requested, when basedCurrency is EUR, then return latest rates from database`() {
        runBlocking {
            `when`(ratesDao.getLatestRatesFromBase(TEST_BASE_CURRENCY)).thenReturn(currencyEntity)

            val response = currencyDatabaseService.latestCurrencyRates(TEST_BASE_CURRENCY)

            verify(ratesDao).getLatestRatesFromBase(TEST_BASE_CURRENCY)

            response.onSuccess {
                assertEquals(it?.rates?.first()?.countryCode, TEST_COUNTRY_CODE)
                assertEquals(it?.rates?.first()?.value, TEST_RATE)
            }
        }
    }

    @Test
    fun `given updateRates are requested, when basedCurrency is EUR, then return latest rates from database`() {
        runBlocking {
            currencyDatabaseService.updateRates(currencyEntity)

            verify(ratesDao).insertOrUpdate(currencyEntity)
        }
    }

    companion object {
        private const val TEST_COUNTRY_CODE = "USD"
        private const val TEST_BASE_CURRENCY = "EUR"
        private val TEST_RATE = BigDecimal(12.45775)
    }
}
