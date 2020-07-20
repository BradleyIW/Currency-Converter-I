package com.bradley.wilson.currency.usecase

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.currency.data.CurrencyRepository
import com.bradley.wilson.testing.mockito.eq
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify

class GetLatestRatesUseCaseTest : UnitTest() {

    private lateinit var useCase: GetLatestRatesUseCase

    @Mock
    private lateinit var repository: CurrencyRepository

    @Before
    fun setup() {
        useCase = GetLatestRatesUseCase(repository)
    }

    @Test
    fun `given use is executed, when base currency is EUR, then request the repository for latestCurrencies`() {
        runBlocking {
            val params = GetLatestRatesParams(EUR_BASE_CURRENCY)

            useCase.run(params)

            verify(repository).latestCurrencyRates(eq(EUR_BASE_CURRENCY))
        }
    }

    companion object {
        private const val EUR_BASE_CURRENCY = "EUR"
    }
}
