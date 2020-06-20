package com.bradley.wilson.currency.data.remote

import com.bradley.wilson.currency.data.remote.responses.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET(LATEST_ENDPOINT)
    suspend fun latestCurrencyRates(
        @Query(BASE_CURRENCY) baseCurrency: String
    ): Response<CurrencyResponse>

    companion object {
        private const val LATEST_ENDPOINT = "latest"
        private const val BASE_CURRENCY = "baseCurrency"
    }
}