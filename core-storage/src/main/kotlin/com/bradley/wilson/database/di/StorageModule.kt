package com.bradley.wilson.database.di

import androidx.room.Room
import com.bradley.wilson.database.currency.CurrencyDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val storageModule: Module = module {
    single {
        Room.databaseBuilder(
            androidContext(), CurrencyDatabase::class.java, CurrencyDatabase.DB_NAME
        ).build()
    }
    factory { get<CurrencyDatabase>().ratesDao() }
}
