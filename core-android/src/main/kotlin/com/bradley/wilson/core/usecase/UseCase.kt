package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import kotlinx.coroutines.*

interface UseCase<in Params, out Type> {

    suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        val backgroundJob = scope.async(dispatcher) { run(params) }
        scope.launch {
            onResult(backgroundJob.await())
        }
    }
}