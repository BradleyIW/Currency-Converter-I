package com.bradley.wilson.currency.data.local

import com.bradley.wilson.database.currency.rates.RatesDao
import com.bradley.wilson.database.currency.rates.CurrencyEntity
import com.bradley.wilson.database.service.DatabaseService

class CurrencyDatabaseService(private val ratesDao: RatesDao) : DatabaseService() {

    suspend fun latestCurrencyRates(baseCurrency: String) = request {
        ratesDao.getLatestRatesFromBase(baseCurrency)
    }

    suspend fun updateRates(currencyEntity: CurrencyEntity) = request {
        ratesDao.insertRate(currencyEntity)
    }
}
