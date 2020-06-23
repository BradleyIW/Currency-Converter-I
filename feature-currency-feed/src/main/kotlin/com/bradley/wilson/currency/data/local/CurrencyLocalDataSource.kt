package com.bradley.wilson.currency.data.local

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.database.currency.rates.RatesEntity

class CurrencyLocalDataSource(private val currencyDatabaseService: CurrencyDatabaseService) {

    suspend fun latestCurrencyRates(baseCurrency: String): Either<Failure, RatesEntity> =
        currencyDatabaseService.latestCurrencyRates(baseCurrency)

    suspend fun saveRates(ratesEntity: RatesEntity): Either<Failure, Unit> =
        currencyDatabaseService.updateRates(ratesEntity)

}
