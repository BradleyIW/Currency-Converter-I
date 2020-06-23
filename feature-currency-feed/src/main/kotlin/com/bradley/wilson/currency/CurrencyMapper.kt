package com.bradley.wilson.currency

import com.bradley.wilson.currency.data.remote.responses.CurrencyResponse
import com.bradley.wilson.currency.usecase.Currency
import com.bradley.wilson.database.currency.rates.CurrencyRate
import com.bradley.wilson.database.currency.rates.RatesEntity

class CurrencyMapper {

    fun toCurrencyList(currencyResponse: CurrencyResponse?) = currencyResponse?.let {
        currencyResponse.rates.map { rateEntry ->
            Currency(rateEntry.key, rateEntry.value)
        }
    } ?: emptyList()

    fun toCurrencyList(ratesEntity: RatesEntity?) = ratesEntity?.let {
        ratesEntity.rates.map {
            Currency(it.countryCode, it.value)
        }
    } ?: emptyList()

    fun toRatesEntity(baseCurrency: String, rates: List<Currency>) =
        RatesEntity(baseCurrency, rates.map {
            CurrencyRate(it.country, it.rate)
        })
}
