package com.bradley.wilson.currency

import com.bradley.wilson.currency.data.remote.responses.CurrencyResponse
import com.bradley.wilson.currency.feed.Currency
import com.bradley.wilson.currency.feed.CurrencyItem
import com.bradley.wilson.database.currency.rates.CurrencyEntity
import com.bradley.wilson.database.currency.rates.CurrencyRate
import java.math.BigDecimal

class CurrencyMapper {

    fun toCurrencyList(currencyResponse: CurrencyResponse?) = currencyResponse?.let {
        currencyResponse.rates.map { rateEntry ->
            Currency(rateEntry.key, BigDecimal(rateEntry.value), DEFAULT_TIMESTAMP)
        }
    } ?: emptyList()

    fun toCurrencyList(currencyEntity: CurrencyEntity?) = currencyEntity?.let {
        currencyEntity.rates.map {
            Currency(it.countryCode, it.value, currencyEntity.modifiedAt)
        }
    } ?: emptyList()

    fun toRatesEntity(baseCurrency: String, rates: List<Currency>) =
        CurrencyEntity(baseCurrency, rates.map {
            CurrencyRate(it.country, it.rate)
        }, DEFAULT_TIMESTAMP, DEFAULT_TIMESTAMP)

    fun toCurrencyItem(it: Currency) =
        CurrencyItem(it.country, it.rate, lastUpdatedAt = it.lastUpdatedAt)

    companion object {
        private const val DEFAULT_TIMESTAMP = 0L
    }
}
