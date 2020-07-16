package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
abstract class LongPollingUseCase<Params, Type> : UseCase<Params, Type> {

    fun execute(
        params: Params,
        scope: CoroutineScope,
        intervalMillis: Long = POLLING_INTERVAL_MILLIS,
        result: (Either<Failure, Type>) -> Unit
    ) {
        scope.coroutineContext.cancelChildren()
        scope.launch {
            while (true) {
                val backgroundJob = async(scope.coroutineContext) { run(params) }
                result(backgroundJob.await())
                delay(intervalMillis)
            }
        }
    }

    companion object {
        private const val POLLING_INTERVAL_MILLIS = 1000L

    }
}
