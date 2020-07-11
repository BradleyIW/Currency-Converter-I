package com.bradley.wilson.database.currency.rates

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.bradley.wilson.FunctionalTest
import com.bradley.wilson.core.database.currency.rates.CurrencyEntity
import com.bradley.wilson.core.database.currency.rates.CurrencyRate
import com.bradley.wilson.core.database.currency.rates.RatesDao
import com.bradley.wilson.core.extensions.math.equalTo
import com.bradley.wilson.core.database.currency.CurrencyDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class RateDaoTest : FunctionalTest() {

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
        val rateEntity = CurrencyEntity(baseCurrency, listOf(currencyRate), 0L, 0L)

        dao.insertRate(rateEntity)

        val rates = dao.getLatestRatesFromBase(baseCurrency)

        assertEquals(rates?.baseCurrency, baseCurrency)
        assertEquals(rates?.rates?.first()?.countryCode, TEST_COUNTRY_CODE)
        assertTrue(rates?.rates?.first()?.value?.equalTo(TEST_RATE) ?: true)
    }

    @Test
    fun givenOneRateValueUpdated_whenBaseCurrencyIsEUR_thenAssertValuesAreStoredAndRetrievedProperly() {
        val baseCurrency = "EUR"
        val insertedCurrencyRate = CurrencyRate(TEST_COUNTRY_CODE, TEST_RATE)
        val insertedRate = CurrencyEntity(baseCurrency, listOf(insertedCurrencyRate), 0L, 0L)

        val updatedCurrencyRate = CurrencyRate(TEST_COUNTRY_CODE, UPDATED_TEST_RATE)
        val updatedRate = CurrencyEntity(baseCurrency, listOf(updatedCurrencyRate), 0L, 0L)

        dao.insertRate(insertedRate)
        dao.updateRate(updatedRate)

        val rates = dao.getLatestRatesFromBase(baseCurrency)

        assertEquals(rates?.baseCurrency, baseCurrency)
        assertEquals(rates?.rates?.first()?.countryCode, TEST_COUNTRY_CODE)
        assertTrue(rates?.rates?.first()?.value?.equalTo(UPDATED_TEST_RATE) ?: true)
    }

    @Test
    fun givenTableIsNotEmpty_whenInsertOrUpdateIsCalled_thenTimestampsShouldBeDifferent() {
        val baseCurrency = "EUR"

        val testRate = CurrencyRate(TEST_COUNTRY_CODE, TEST_RATE)
        val testEntity = CurrencyEntity(baseCurrency, listOf(testRate), 0L, 0L)

        dao.insertRate(testEntity)

        dao.insertOrUpdate(testEntity)

        val newRate = dao.getLatestRatesFromBase(baseCurrency)

        assertTrue(newRate?.modifiedAt != newRate?.createdAt)

    }

    @Test
    fun givenTableIsEmpty_whenInsertOrUpdateIsCalled_thenTimestampsShouldBeTheSame() {
        val baseCurrency = "EUR"

        val testRate = CurrencyRate(TEST_COUNTRY_CODE, TEST_RATE)
        val testEntity = CurrencyEntity(baseCurrency, listOf(testRate), 0L, 0L)

        dao.insertOrUpdate(testEntity)

        val newRate = dao.getLatestRatesFromBase(baseCurrency)

        assertTrue(newRate?.modifiedAt == newRate?.createdAt)

    }

    companion object {
        private const val TEST_COUNTRY_CODE = "USD"
        private val TEST_RATE = BigDecimal(1.123345)
        private val UPDATED_TEST_RATE = BigDecimal(1.1234543)
    }
}
