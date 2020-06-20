package com.bradley.wilson.currency.data

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.currency.usecase.Currency

interface CurrencyRepository {
    suspend fun latestCurrencyRates(baseCurrency: String): Either<Failure, List<Currency>>
}