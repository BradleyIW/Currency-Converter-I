package com.bradley.wilson.database.currency.rates

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.bradley.wilson.database.currency.CurrencyDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RateDaoTest {

    private lateinit var database: CurrencyDatabase
    private lateinit var dao: RatesDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, CurrencyDatabase::class.java).build()
        dao = database.ratesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun givenOneRateValueInserted_whenBaseCurrencyIsEUR_thenAssertValuesAreStoredAndRetrievedProperly() {
        val baseCurrency = "EUR"
        val currencyRate = CurrencyRate(TEST_COUNTRY_CODE, TEST_RATE)
        val rateEntity = RatesEntity(baseCurrency, listOf(currencyRate))

        dao.insertRate(rateEntity)

        val rates = dao.getLatestRatesFromBase(baseCurrency)

        assertEquals(rates.baseCurrency, baseCurrency)
        assertEquals(rates.rates.first().countryCode, TEST_COUNTRY_CODE)
        assertEquals(rates.rates.first().value, TEST_RATE, 0.0)
    }

    companion object {
        private const val TEST_COUNTRY_CODE = "USD"
        private const val TEST_RATE = 1.123345
    }
}