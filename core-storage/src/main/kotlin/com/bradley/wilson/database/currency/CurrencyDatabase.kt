package com.bradley.wilson.database.currency

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bradley.wilson.database.currency.rates.CurrencyEntity
import com.bradley.wilson.database.currency.rates.RatesDao

@Database(
    entities = [CurrencyEntity::class],
    version = CurrencyDatabase.VERSION
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun ratesDao(): RatesDao

    companion object {
        const val VERSION = 1
        const val DB_NAME: String = "CurrencyDatabase"
    }
}
