package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.extensions.math.equalTo
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.functional.onSuccess
import com.bradley.wilson.coroutines.CoroutinesTestRule
import com.bradley.wilson.currency.feed.Currency
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class LongPollingUseCaseTest : UnitTest() {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule(testDispatcher)

    private lateinit var longPollingUseCase: LongPollingUseCase<Unit, Currency>

    private lateinit var spiedUseCase: LongPollingUseCase<Unit, Currency>

    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        longPollingUseCase = object : LongPollingUseCase<Unit, Currency>() {
            override suspend fun run(params: Unit): Either<Failure, Currency> =
                //Dummy response from a repository
                Either.Right(Currency(TEST_COUNTRY_CODE, TEST_RATE, 0L))
        }
        spiedUseCase = Mockito.spy(longPollingUseCase)
    }

    @After
    fun tearDown() {
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `given polling use case is executed, when execution last 5 intervals, then use-case run() should be called every interval`() {
        runBlocking {
            val intervalMillis = 100L

            spiedUseCase.execute(Unit, testScope, intervalMillis) { result ->
                result.onSuccess {
                    assertEquals(it.country, TEST_COUNTRY_CODE)
                    assertTrue(it.rate.equalTo(TEST_RATE))
                }
            }

            Thread.sleep(5 * intervalMillis)

            verify(spiedUseCase, times(5)).run(Unit)
        }
    }

    companion object {
        private const val TEST_COUNTRY_CODE = "USD"
        private val TEST_RATE = BigDecimal(1.23466)
    }
}

