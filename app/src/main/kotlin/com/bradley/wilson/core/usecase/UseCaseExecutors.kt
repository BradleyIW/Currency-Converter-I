package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

interface UseCaseExecutors {

    operator fun <Params, Type> UseCase<Params, Type>.invoke(
        params: Params,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        result: (Either<Failure, Type>) -> Unit
    )

    operator fun <Params, Type> UseCase<Params, Type>.invoke(
        params: Params,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        intervalMillis: Long,
        result: (Either<Failure, Type>) -> Unit
    )

    fun cancelJobs()
}
