package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.functional.Either
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class CurrencyUseCaseExecutorsTest : UnitTest() {

    private lateinit var executor: CurrencyUseCaseExecutors

    @Mock
    private lateinit var useCase: UseCase<Int, String>

    @Before
    fun setUp() {
        executor = CurrencyUseCaseExecutors()
    }

    @Test
    fun `given a scope and a use case, when invoke is called on use case, then applies onResult to returned value`() {
        runBlocking {
            val param = 3
            val result = Either.Right("3")
            `when`(useCase.run(param)).thenReturn(result)

            with(executor) {
                useCase.invoke(param, this@runBlocking, Dispatchers.IO) {
                    assertEquals(it, result)
                }
            }
        }
    }
}
