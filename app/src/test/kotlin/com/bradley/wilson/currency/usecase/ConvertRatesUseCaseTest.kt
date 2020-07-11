package com.bradley.wilson.currency.usecase

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.functional.onSuccess
import com.bradley.wilson.currency.feed.Currency
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class ConvertRatesUseCaseTest : UnitTest() {

    private lateinit var convertRatesUseCase: ConvertRatesUseCase

    @Before
    fun setup() {
        convertRatesUseCase = ConvertRatesUseCase()
    }

    @Test
    fun `given a multiplier of 10 and a set of rates, then each rate should be multiplied by 10`() {
        val ukTestRate = BigDecimal(1.1245547747747444)
        val usTestRate = BigDecimal(2.7578444456544456)
        val multiplier = BigDecimal.TEN


        val dummyList = listOf(
            Currency("GBP", ukTestRate, 0L),
            Currency("USD", usTestRate, 0L)
        )
        val params = ConvertRatesParams(dummyList, multiplier)

        runBlocking {
            val response = convertRatesUseCase.run(params)
            response.onSuccess {
                assertEquals(it.first().rate, ukTestRate.multiply(multiplier))
                assertEquals(it[1].rate, usTestRate.multiply(multiplier))
            }
        }
    }
}

