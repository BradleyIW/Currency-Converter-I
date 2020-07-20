package com.bradley.wilson.currency.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.extensions.math.equalsZero
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.usecase.UseCase
import java.math.BigDecimal

class ConvertRatesUseCase : UseCase<ConvertRatesParams, List<Currency>> {

    override suspend fun run(params: ConvertRatesParams): Either<Failure, List<Currency>> =
        Either.Right(convertCurrencies(params))

    private fun convertCurrencies(params: ConvertRatesParams): List<Currency> =
        params.currencies.map {
            multiplyValues(it, params.amount)
        }

    private fun multiplyValues(currency: Currency, amount: BigDecimal) =
        if (amount.equalsZero()) {
            currency.copy(rate = amount)
        } else {
            currency.copy(rate = currency.rate.multiply(amount))
        }
}

data class ConvertRatesParams(val currencies: List<Currency>, val amount: BigDecimal)
