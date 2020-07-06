package com.bradley.wilson.database.currency.rates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RatesDao {
    @Query("SELECT * FROM Rates WHERE baseCurrency = :baseCurrency")
    fun getLatestRatesFromBase(baseCurrency: String): CurrencyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRate(currencyEntity: CurrencyEntity)
}
