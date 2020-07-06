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
            Currency(rateEntry.key, BigDecimal(rateEntry.value))
        }
    } ?: emptyList()

    fun toCurrencyList(currencyEntity: CurrencyEntity?) = currencyEntity?.let {
        currencyEntity.rates.map {
            Currency(it.countryCode, BigDecimal(it.value))
        }
    } ?: emptyList()

    fun toRatesEntity(baseCurrency: String, rates: List<Currency>) =
        CurrencyEntity(baseCurrency, rates.map {
            CurrencyRate(it.country, it.rate.toPlainString())
        })

    fun toCurrencyItem(it: Currency) =
        CurrencyItem(it.country, it.rate)
}
