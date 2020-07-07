package com.bradley.wilson.database.currency.rates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
abstract class RatesDao {

    @Query("SELECT * FROM Rates WHERE baseCurrency = :baseCurrency")
    abstract fun getLatestRatesFromBase(baseCurrency: String): CurrencyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRate(currencyEntity: CurrencyEntity)

    @Update
    abstract fun updateRate(CurrencyEntity: CurrencyEntity)

    fun insertOrUpdate(currencyEntity: CurrencyEntity) {
        val entity = getLatestRatesFromBase(currencyEntity.baseCurrency)
        entity?.let {
            updateWithTimestamp(currencyEntity)
        } ?: insertWithTimestamp(currencyEntity)
    }

    private fun insertWithTimestamp(CurrencyEntity: CurrencyEntity) {
        insertRate(CurrencyEntity.apply {
            createdAt = System.currentTimeMillis()
            modifiedAt = System.currentTimeMillis()
        })
    }

    private fun updateWithTimestamp(CurrencyEntity: CurrencyEntity) {
        updateRate(CurrencyEntity.apply {
            modifiedAt = System.currentTimeMillis()
        })
    }
}
