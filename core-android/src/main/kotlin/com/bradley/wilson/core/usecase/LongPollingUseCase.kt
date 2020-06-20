package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import kotlinx.coroutines.*

interface LongPollingUseCase<in Params, Type> {

    suspend fun run(params: Params): Either<Failure, Type>

    suspend operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        delay: Long = DEFAULT_DELAY_INTERVAL,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        scope.launch {
            while (isActive) {
                val backgroundJob = scope.async(dispatcher) { run(params) }
                onResult(backgroundJob.await())
                withContext(Dispatchers.Default) {
                    delay(delay)
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_DELAY_INTERVAL = 1000L
    }
}