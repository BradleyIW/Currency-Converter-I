package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.coroutines.CoroutinesTestRule
import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.functional.onSuccess
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

@ExperimentalCoroutinesApi
class LongPollingUseCaseTest : UnitTest() {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule(testDispatcher)

    private data class TestRate(
        val code: String,
        val value: Double
    )

    private lateinit var longPollingUseCase: LongPollingUseCase<Unit, TestRate>

    private lateinit var spiedUseCase: LongPollingUseCase<Unit, TestRate>

    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        longPollingUseCase = object : LongPollingUseCase<Unit, TestRate>() {
            override suspend fun run(params: Unit): Either<Failure, TestRate> =
                //Dummy response from a repository
                Either.Right(TestRate(TEST_COUNTRY_CODE, TEST_RATE))
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
                    assertEquals(it.code, TEST_COUNTRY_CODE)
                    assertEquals(it.value, TEST_RATE, 0.0)
                }
            }

            Thread.sleep(5 * intervalMillis)

            verify(spiedUseCase, times(5)).run(Unit)
        }
    }

    companion object {
        private const val TEST_COUNTRY_CODE = "USD"
        private const val TEST_RATE = 1.234664
    }
}

