package com.bradley.wilson.currency.data.remote

import com.bradley.wilson.core.network.api.ApiService

class CurrencyApiService(private val currencyApi: CurrencyApi) : ApiService() {

    suspend fun latestCurrencyRates(baseCurrency: String) =
        request { currencyApi.latestCurrencyRates(baseCurrency) }

}
