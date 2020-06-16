package com.bradley.wilson.currency.data

import com.bradley.wilson.core.functional.map
import com.bradley.wilson.currency.CurrencyMapper
import com.bradley.wilson.currency.data.remote.CurrencyRemoteDataSource

class CurrencyDataSource(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource,
    private val currencyMapper: CurrencyMapper = CurrencyMapper()
) : CurrencyRepository {

    override suspend fun latestCurrencyRates(baseCurrency: String) =
        currencyRemoteDataSource.latestCurrencyRates(baseCurrency)
            .map { currencyMapper.toCurrencyList(it) }

}

