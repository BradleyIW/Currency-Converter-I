package com.bradley.wilson.currency.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

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
