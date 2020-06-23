package com.bradley.wilson.database.currency.rates

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bradley.wilson.database.currency.rates.converter.CurrencyRateConverter

@Entity(tableName = "Rates")
@TypeConverters(CurrencyRateConverter::class)
data class RatesEntity(
    @PrimaryKey
    @ColumnInfo(name = "baseCurrency")
    val baseCurrency: String,

    @ColumnInfo(name = "rates")
    val rates: List<CurrencyRate>
)
