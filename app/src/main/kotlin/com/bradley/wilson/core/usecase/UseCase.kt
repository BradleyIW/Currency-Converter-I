package com.bradley.wilson.core.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either

interface UseCase<in Params, out Type> {
    suspend fun run(params: Params): Either<Failure, Type>
}
