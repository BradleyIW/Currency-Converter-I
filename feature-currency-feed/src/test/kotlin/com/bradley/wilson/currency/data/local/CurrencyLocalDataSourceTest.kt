package com.bradley.wilson.currency.data.local

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.mockito.eq
import com.bradley.wilson.database.currency.rates.RatesEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CurrencyLocalDataSourceTest : UnitTest() {

    private lateinit var currencyLocalDataSource: CurrencyLocalDataSource

    @Mock
    private lateinit var currencyDatabaseService: CurrencyDatabaseService

    @Before
    fun setup() {
        currencyLocalDataSource = CurrencyLocalDataSource(currencyDatabaseService)
    }

    @Test
    fun `given base currency, when latestCurrencyRates is requested, then get latest currency rates from database service`() {
        runBlocking {
            currencyLocalDataSource.latestCurrencyRates(TEST_BASE_CURRENCY)

            verify(currencyDatabaseService).latestCurrencyRates(eq(TEST_BASE_CURRENCY))
        }
    }

    @Test
    fun `given rates eneity, when save rates is requested, then update database service with new rates`() {
        runBlocking {
            val ratesEntity: RatesEntity = mock(RatesEntity::class.java)

            currencyLocalDataSource.saveRates(ratesEntity)

            verify(currencyDatabaseService).updateRates(eq(ratesEntity))
        }
    }

    companion object {
        private const val TEST_BASE_CURRENCY = "EUR"
    }

}


