package com.bradley.wilson.currency.data.remote

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.currency.data.remote.responses.CurrencyResponse

class CurrencyRemoteDataSource(private val currencyApiService: CurrencyApiService) {

    suspend fun latestCurrencyRates(baseCurrency: String): Either<Failure, CurrencyResponse> =
        currencyApiService.latestCurrencyRates(baseCurrency)

}