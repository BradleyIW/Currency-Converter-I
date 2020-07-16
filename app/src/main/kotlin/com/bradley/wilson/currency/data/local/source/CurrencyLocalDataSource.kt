package com.bradley.wilson.currency.data.local.source

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.currency.data.local.CurrencyEntity

class CurrencyLocalDataSource(private val currencyDatabaseService: CurrencyDatabaseService) {

    suspend fun latestCurrencyRates(baseCurrency: String): Either<Failure, CurrencyEntity?> =
        currencyDatabaseService.latestCurrencyRates(baseCurrency)

    suspend fun saveRates(currencyEntity: CurrencyEntity): Either<Failure, Unit> =
        currencyDatabaseService.updateRates(currencyEntity)

}
