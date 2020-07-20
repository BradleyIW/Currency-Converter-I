package com.bradley.wilson.currency.data

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.functional.onFailure
import com.bradley.wilson.core.functional.onSuccess
import com.bradley.wilson.core.network.error.ServerError
import com.bradley.wilson.currency.CurrencyMapper
import com.bradley.wilson.currency.data.local.CurrencyEntity
import com.bradley.wilson.currency.data.local.CurrencyRate
import com.bradley.wilson.currency.data.local.source.CurrencyLocalDataSource
import com.bradley.wilson.currency.data.remote.CurrencyRemoteDataSource
import com.bradley.wilson.currency.data.remote.responses.CurrencyResponse
import com.bradley.wilson.currency.usecase.Currency
import com.bradley.wilson.testing.mockito.any
import com.bradley.wilson.testing.mockito.eq
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.math.BigDecimal

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
    fun `given based currency, when latestCurrencyRates succeeds, then propagate list of currencies and save to database`() {
        runBlocking {
            val response = CurrencyResponse(TEST_BASE_EUR_CURRENCY, mapOf(Pair(TEST_COUNTRY, TEST_RATE.toDouble())))

            `when`(remoteDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(Either.Right(response))

            val repoResponse = repository.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            verify(remoteDataSource).latestCurrencyRates(TEST_BASE_EUR_CURRENCY)
            verify(localDataSource).saveRates(any())
            verify(mapper).toCurrencyList(eq(response))

            assertSuccessResponse(repoResponse)
        }
    }

    @Test
    fun `given getCurrenciesByBase is called, remote data source request fails and local data source succeeds, then propagate list`() {
        runBlocking {
            val entity = CurrencyEntity(TEST_BASE_EUR_CURRENCY, listOf(CurrencyRate(TEST_COUNTRY, TEST_RATE)), 0L, 0L)

            `when`(remoteDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(Either.Left(ServerError))
            `when`(localDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(Either.Right(entity))

            val repoResponse = repository.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            verify(remoteDataSource).latestCurrencyRates(TEST_BASE_EUR_CURRENCY)
            verify(localDataSource).latestCurrencyRates(TEST_BASE_EUR_CURRENCY)
            verify(mapper).toCurrencyList(eq(entity))

            assertSuccessResponse(repoResponse)
        }
    }

    @Test
    fun `given getCurrenciesByBase is called, remote data source request fails and local data source fails, then propagate error`() {
        runBlocking {
            `when`(remoteDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(Either.Left(ServerError))
            `when`(localDataSource.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)).thenReturn(Either.Left(ServerError))

            val response = repository.latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            verify(remoteDataSource).latestCurrencyRates(TEST_BASE_EUR_CURRENCY)

            response.onFailure { assertEquals(it, ServerError) }
            assertTrue(response.isLeft)
        }
    }

    private fun assertSuccessResponse(repoResponse: Either<Failure, List<Currency>>) {
        repoResponse.onSuccess {
            it.map { currency ->
                assertEquals(currency.country, TEST_COUNTRY)
                assertEquals(currency.rate, TEST_RATE)
            }
        }
        assertTrue(repoResponse.isRight)
    }


    companion object {
        private const val TEST_COUNTRY = "USD"
        private val TEST_RATE = BigDecimal(1.224646)
        private const val TEST_BASE_EUR_CURRENCY = "EUR"
    }
}

