package com.bradley.wilson.currency.usecase

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.usecase.LongPollingUseCase
import com.bradley.wilson.currency.data.CurrencyRepository

class GetLatestRatesUseCase(private val repository: CurrencyRepository) :
    LongPollingUseCase<GetLatestRatesParams, List<Currency>> {

    override suspend fun run(params: GetLatestRatesParams): Either<Failure, List<Currency>> =
        repository.latestCurrencyRates(params.baseCurrency)
}

data class GetLatestRatesParams(val baseCurrency: String)