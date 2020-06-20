package com.bradley.wilson.currency.data

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.eq
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.currency.CurrencyMapper
import com.bradley.wilson.currency.data.local.CurrencyLocalDataSource
import com.bradley.wilson.currency.data.remote.CurrencyRemoteDataSource
import com.bradley.wilson.currency.data.remote.responses.CurrencyResponse
import com.bradley.wilson.network.error.ServerError
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class CurrencyDataSourceTest : UnitTest() {

    private lateinit var repository: CurrencyRepository

    @Mock
    private lateinit var remoteDataSource: CurrencyRemoteDataSource

    @Mock
    private lateinit var localDataSource: CurrencyLocalDataSource

    @Mock
    private lateinit var mapper: CurrencyMapper

    @Before
    fun setup() {
        repository = CurrencyDataSource(remoteDataSource, localDataSource, mapper)
    }

    @Test
    fun `given getCurrenciesByBase is called, remote data source request succeeds, then propagate mapped currencies`() {
        runBlocking {
            val currencyResponse = CurrencyResponse(TEST_BASE_EUR_CURRENCY, mapOf())

            `when`(remoteDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(
                Either.Right(
                    currencyResponse
                )
            )
            val response = repository.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            verify(remoteDataSource).latestCurrencyRates(TEST_BASE_EUR_CURRENCY)
            verify(mapper).toCurrencyList(eq(currencyResponse))

            assertTrue(response.isRight)
        }
    }

    @Test
    fun `given getCurrenciesByBase is called, remote data source request fails, then propagate error`() {
        runBlocking {
            `when`(remoteDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(
                Either.Left(
                    ServerError
                )
            )
            val response = repository.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            verify(remoteDataSource).latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            assertTrue(response.isLeft)

        }
    }

    companion object {
        private const val TEST_BASE_EUR_CURRENCY = "EUR"
    }
}