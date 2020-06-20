package com.bradley.wilson.database.currency.rates

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "Rates")
@TypeConverters(CurrencyRateConverter::class)
data class RatesEntity(
    @PrimaryKey
    @ColumnInfo(name = "baseCurrency")
    val baseCurrency: String,

    @ColumnInfo(name = "rates")
    val rates: List<CurrencyRate>
)

class CurrencyRateConverter {

    @TypeConverter
    fun fromJsonToRates(jsonString: String?): List<CurrencyRate?> =
        jsonString?.let { json ->
            val type = object : TypeToken<List<CurrencyRate?>>() {}.type
            Gson().fromJson<List<CurrencyRate>>(json, type).map {
                CurrencyRate(it.countryCode, it.value)
            }
        } ?: emptyList()

    @TypeConverter
    fun fromRatesToJson(rates: List<CurrencyRate>): String? =
        Gson().toJson(rates)
}
