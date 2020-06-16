package com.bradley.wilson.currency

import com.bradley.wilson.currency.data.remote.responses.CurrencyResponse
import com.bradley.wilson.currency.usecase.Currency

class CurrencyMapper {

    fun toCurrencyList(currencyResponse: CurrencyResponse) =
        currencyResponse.rates.map { rates ->
            Currency(rates.key, rates.value)
        }
}