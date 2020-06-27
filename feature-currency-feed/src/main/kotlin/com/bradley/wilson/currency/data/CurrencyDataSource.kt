package com.bradley.wilson.currency.data

import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.functional.fallback
import com.bradley.wilson.core.functional.map
import com.bradley.wilson.currency.CurrencyMapper
import com.bradley.wilson.currency.data.local.CurrencyLocalDataSource
import com.bradley.wilson.currency.data.remote.CurrencyRemoteDataSource
import com.bradley.wilson.currency.feed.Currency

class CurrencyDataSource(
    private val remoteDataSource: CurrencyRemoteDataSource,
    private val localDataSource: CurrencyLocalDataSource,
    private val currencyMapper: CurrencyMapper
) : CurrencyRepository {

    override suspend fun latestCurrencyRates(baseCurrency: String) =
        getLatestCurrenciesRemotely(baseCurrency)
            .fallback {
                localDataSource.latestCurrencyRates(baseCurrency).map {
                    currencyMapper.toCurrencyList(it)
                }
            }.finally {
                localDataSource.saveRates(currencyMapper.toRatesEntity(baseCurrency, it))
            }.execute()

    private suspend fun getLatestCurrenciesRemotely(baseCurrency: String): suspend () -> (Either<Failure, List<Currency>>) =
        {
            remoteDataSource.latestCurrencyRates(baseCurrency).map {
                currencyMapper.toCurrencyList(it)
            }
        }

}


