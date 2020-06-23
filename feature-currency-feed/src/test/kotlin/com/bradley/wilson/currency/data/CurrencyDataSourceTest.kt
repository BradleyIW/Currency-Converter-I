package com.bradley.wilson.currency.data

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.functional.onFailure
import com.bradley.wilson.core.functional.onSuccess
import com.bradley.wilson.core.mockito.any
import com.bradley.wilson.core.mockito.eq
import com.bradley.wilson.currency.CurrencyMapper
import com.bradley.wilson.currency.data.local.CurrencyLocalDataSource
import com.bradley.wilson.currency.data.remote.CurrencyRemoteDataSource
import com.bradley.wilson.currency.data.remote.responses.CurrencyResponse
import com.bradley.wilson.database.currency.rates.CurrencyRate
import com.bradley.wilson.database.currency.rates.RatesEntity
import com.bradley.wilson.network.error.ServerError
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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
    fun `given getCurrenciesByBase is called, remote data source request succeeds, then propagate list of currencies and save to database`() {
        runBlocking {
            val response = CurrencyResponse(TEST_BASE_EUR_CURRENCY, mapOf(Pair(TEST_COUNTRY, TEST_RATE)))

            `when`(remoteDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(Either.Right(response))

            val repoResponse = repository.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            verify(remoteDataSource).latestCurrencyRates(TEST_BASE_EUR_CURRENCY)
            verify(localDataSource).saveRates(any())
            verify(mapper).toCurrencyList(eq(response))

            repoResponse.onSuccess {
                it.map { currency ->
                    assertEquals(currency.country, TEST_COUNTRY)
                    assertEquals(currency.rate, TEST_RATE, 0.0)
                }
            }
            assertTrue(repoResponse.isRight)
        }
    }

    @Test
    fun `given getCurrenciesByBase is called, remote data source request fails and local data source succeeds, then propagate list`() {
        runBlocking {
            val entity = RatesEntity(TEST_BASE_EUR_CURRENCY, listOf(CurrencyRate(TEST_COUNTRY, TEST_RATE)))

            `when`(remoteDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(Either.Left(ServerError))
            `when`(localDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(Either.Right(entity))

            val repoResponse = repository.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            verify(remoteDataSource).latestCurrencyRates(TEST_BASE_EUR_CURRENCY)
            verify(localDataSource).latestCurrencyRates(TEST_BASE_EUR_CURRENCY)
            verify(mapper).toCurrencyList(eq(entity))

            repoResponse.onSuccess {
                it.map { currency ->
                    assertEquals(currency.country, TEST_COUNTRY)
                    assertEquals(currency.rate, TEST_RATE, 0.0)
                }
            }
            assertTrue(repoResponse.isRight)
        }
    }

    @Test
    fun `given getCurrenciesByBase is called, remote data source request fails and local data source fails, then propagate error`() {
        runBlocking {
            `when`(remoteDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(
                Either.Left(ServerError)
            )
            `when`(localDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(
                Either.Left(ServerError)
            )
            val response = repository.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            verify(remoteDataSource).latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            response.onFailure {
                assertEquals(it, ServerError)
            }
            assertTrue(response.isLeft)

        }
    }

    companion object {
        private const val TEST_COUNTRY = "USD"
        private const val TEST_RATE = 1.224646
        private const val TEST_BASE_EUR_CURRENCY = "EUR"
    }
}