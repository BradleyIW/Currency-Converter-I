package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CurrencyUseCaseExecutors : UseCaseExecutors {

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

                //Weird behaviour where it'll throw IllegalStateException in tests
                //for a null value from await()
                backgroundJob.await()?.let {
                    result(it)
                }

                withContext(Dispatchers.Default) {
                    delay(intervalMillis)
                }
            }
        }
    }

    override fun cancelJobs() {
        runBlocking {
            pollingJob?.cancelAndJoin()
        }
    }
}

