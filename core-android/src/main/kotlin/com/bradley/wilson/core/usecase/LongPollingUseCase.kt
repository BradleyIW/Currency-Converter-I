package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import kotlinx.coroutines.*

abstract class LongPollingUseCase<Params, Type> : UseCase<Params, Type> {

    fun execute(
        params: Params,
        scope: CoroutineScope,
        intervalMillis: Long,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        result: (Either<Failure, Type>) -> Unit
    ) {
        scope.launch {
            while (isActive) {
                val backgroundJob = async(dispatcher) { run(params) }
                result(backgroundJob.await())

                withContext(Dispatchers.Default) {
                    delay(intervalMillis)
                }
            }
        }
    }
}