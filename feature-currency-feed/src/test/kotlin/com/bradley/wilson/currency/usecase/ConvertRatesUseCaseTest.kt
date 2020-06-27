package com.bradley.wilson.currency.usecase

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.functional.onSuccess
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConvertRatesUseCaseTest : UnitTest() {

    private lateinit var convertRatesUseCase: ConvertRatesUseCase

    @Before
    fun setup() {
        convertRatesUseCase = ConvertRatesUseCase()
    }

    @Test
    fun `given a multiplier of 10 and a set of rates, then each rate should be multiplied by 10`() {
        val ukTestRate = 1.12455
        val usTestRate = 2.75784
        val multiplier = 10.0


        val dummyList = listOf(
            Currency("GBP", ukTestRate),
            Currency("USD", usTestRate)
        )
        val params = ConvertRatesParams(dummyList, multiplier)

        runBlocking {
            val response = convertRatesUseCase.run(params)
            response.onSuccess {
                assertEquals(it.first().rate, ukTestRate * multiplier, 0.0)
                assertEquals(it[1].rate, usTestRate * multiplier, 0.0)
            }
        }
    }
}