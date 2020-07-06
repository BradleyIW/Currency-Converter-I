package com.bradley.wilson.database.currency.rates

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bradley.wilson.database.currency.rates.converter.BigDecimalTypeConverter
import com.bradley.wilson.database.currency.rates.converter.CurrencyRateConverter

@Entity(tableName = "Rates")
@TypeConverters(CurrencyRateConverter::class, BigDecimalTypeConverter::class)
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "baseCurrency")
    val baseCurrency: String,

    @ColumnInfo(name = "rates")
    val rates: List<CurrencyRate>
)
