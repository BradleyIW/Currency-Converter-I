package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LongPollingUseCaseExecutors : UseCaseExecutors {

    private var pollingJob: Job? = null

    override operator fun <Params, Type> UseCase<Params, Type>.invoke(
        params: Params,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        result: (Either<Failure, Type>) -> Unit
    ) {
        val backgroundJob = scope.async(dispatcher) { run(params) }
        scope.launch {
            result(backgroundJob.await())
        }
    }

    override operator fun <Params, Type> UseCase<Params, Type>.invoke(
        params: Params,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        intervalMillis: Long,
        result: (Either<Failure, Type>) -> Unit
    ) {
        cancelJobs()
        pollingJob = scope.launch {
            while (true) {
                val backgroundJob = async(dispatcher) { run(params) }
                val either = backgroundJob.await()
                either?.let {
                    result(it)
                }
                delay(intervalMillis)

            }
        }
    }

    override fun cancelJobs() {
        runBlocking {
            pollingJob?.cancelAndJoin()
        }
    }
}

