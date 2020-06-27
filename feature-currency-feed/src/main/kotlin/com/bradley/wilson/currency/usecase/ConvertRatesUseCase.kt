package com.bradley.wilson.currency.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.usecase.OneShotUseCase

class ConvertRatesUseCase : OneShotUseCase<ConvertRatesParams, List<Currency>>() {

    override suspend fun run(params: ConvertRatesParams): Either<Failure, List<Currency>> =
        Either.Right(convertCurrencies(params))

    private fun convertCurrencies(params: ConvertRatesParams): List<Currency> =
        params.currencies.map { it.copy(rate = it.rate * params.amount) }
}

data class ConvertRatesParams(val currencies: List<Currency>, val amount: Double)
