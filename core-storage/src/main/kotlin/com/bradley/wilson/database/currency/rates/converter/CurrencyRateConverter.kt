package com.bradley.wilson.database.currency.rates.converter

import androidx.room.TypeConverter
import com.bradley.wilson.database.currency.rates.CurrencyRate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
