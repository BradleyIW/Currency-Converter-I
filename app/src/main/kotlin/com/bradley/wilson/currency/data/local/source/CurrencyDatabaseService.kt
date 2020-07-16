package com.bradley.wilson.currency.data.local.source

import com.bradley.wilson.core.database.service.DatabaseService
import com.bradley.wilson.currency.data.local.CurrencyEntity
import com.bradley.wilson.currency.data.local.RatesDao

class CurrencyDatabaseService(private val ratesDao: RatesDao) : DatabaseService() {

    suspend fun latestCurrencyRates(baseCurrency: String) = request {
        ratesDao.getLatestRatesFromBase(baseCurrency)
    }

    suspend fun updateRates(currencyEntity: CurrencyEntity) = request {
        ratesDao.insertOrUpdate(currencyEntity)
    }
}
