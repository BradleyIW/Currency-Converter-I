package com.bradley.wilson.currency.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.extensions.math.equalsZero
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.usecase.OneShotUseCase
import com.bradley.wilson.currency.feed.Currency
import java.math.BigDecimal

class ConvertRatesUseCase : OneShotUseCase<ConvertRatesParams, List<Currency>>() {

    override suspend fun run(params: ConvertRatesParams): Either<Failure, List<Currency>> =
        Either.Right(convertCurrencies(params))

    private fun convertCurrencies(params: ConvertRatesParams): List<Currency> =
        params.currencies.map {
            if (params.amount.equalsZero()) {
                it.copy(rate = params.amount)
            } else {
                it.copy(rate = it.rate.multiply(params.amount))
            }
        }
}

data class ConvertRatesParams(val currencies: List<Currency>, val amount: BigDecimal)
