package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class LongPollingUseCase<Params, Type> : UseCase<Params, Type> {

    private var pollingJob: Job? = null

    fun execute(
        params: Params,
        scope: CoroutineScope,
        intervalMillis: Long = POLLING_INTERVAL_MILLIS,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        result: (Either<Failure, Type>) -> Unit
    ) {
        pollingJob?.cancel()
        pollingJob = scope.launch {
            while (isActive) {
                val backgroundJob = async(dispatcher) { run(params) }
                result(backgroundJob.await())

                withContext(Dispatchers.Default) {
                    delay(intervalMillis)
                }
            }
        }
    }

    companion object {
        private const val POLLING_INTERVAL_MILLIS = 1000L

    }
}
