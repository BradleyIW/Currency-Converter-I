package com.bradley.wilson.core.functional

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.exceptions.FeatureFailure
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class FallbackOnFailureTest : UnitTest() {

    private object TestError : FeatureFailure()

    private interface SuspendHelper {
        suspend fun primaryAction(): Either<Failure, Unit>

        suspend fun fallbackAction(): Either<Failure, Unit>

        suspend fun fallbackSuccessAction(toSave: Unit)
    }

    @Mock
    private lateinit var suspendHelper: SuspendHelper

    private lateinit var primaryAction: suspend () -> Either<Failure, Unit>

    private lateinit var fallbackAction: suspend () -> Either<Failure, Unit>

    private lateinit var fallbackSuccessAction: suspend (Unit) -> Unit

    @Before
    fun setUp() {
        primaryAction = suspendHelper::primaryAction
        fallbackAction = suspendHelper::fallbackAction
        fallbackSuccessAction = suspendHelper::fallbackSuccessAction
    }

    @Test
    fun `given a suspend function, when fallback method called with a fallbackAction, then returns EitherFallbackWrapper`() {
        val eitherFallbackWrapper = primaryAction.fallback(fallbackAction)
        assertEquals(eitherFallbackWrapper, FallbackOnFailure(primaryAction, fallbackAction))
    }

    @Test
    fun `given a primaryAction with success, when execute called, then does not execute fallbackAction`() {
        runBlocking {
            `when`(suspendHelper.primaryAction()).thenReturn(Either.Right(Unit))

            FallbackOnFailure(primaryAction, fallbackAction).execute()

            verify(suspendHelper).primaryAction()
            verifyNoMoreInteractions(suspendHelper)
        }
    }

    @Test
    fun `given a primaryAction with failure, when execute called, then executes fallbackAction`() {
        runBlocking {
            `when`(suspendHelper.primaryAction()).thenReturn(Either.Left(TestError))

            val fallbackResponse = mock(Either.Left::class.java) as Either.Left<Failure>
            `when`(suspendHelper.fallbackAction()).thenReturn(fallbackResponse)

            FallbackOnFailure(primaryAction, fallbackAction).execute()

            verify(suspendHelper).primaryAction()
            verify(suspendHelper).fallbackAction()
        }
    }

    @Test
    fun `given a fallbackAction with success and fallbackSuccessAction, when execute called, then executes fallbackSuccessAction`() {
        runBlocking {
            `when`(suspendHelper.primaryAction()).thenReturn(Either.Left(TestError))
            `when`(suspendHelper.fallbackAction()).thenReturn(Either.Right(Unit))

            FallbackOnFailure(primaryAction, fallbackAction)
                .finally(fallbackSuccessAction)
                .execute()

            verify(suspendHelper).primaryAction()
            verify(suspendHelper).fallbackAction()
            verify(suspendHelper).fallbackSuccessAction(Unit)
        }
    }

    @Test
    fun `given a fallbackAction with failure and fallbackSuccessAction, when execute called, then does not execute fallbackSuccessAction`() {
        runBlocking {
            `when`(suspendHelper.primaryAction()).thenReturn(Either.Left(TestError))
            `when`(suspendHelper.fallbackAction()).thenReturn(Either.Left(TestError))

            FallbackOnFailure(primaryAction, fallbackAction)
                .finally(fallbackSuccessAction)
                .execute()

            verify(suspendHelper).primaryAction()
            verify(suspendHelper).fallbackAction()
            verifyNoMoreInteractions(suspendHelper)
        }
    }
}
