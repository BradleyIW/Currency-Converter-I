package com.bradley.wilson.database.currency.rates.converter

import androidx.room.TypeConverter
import com.bradley.wilson.core.extensions.primitives.empty
import java.math.BigDecimal

class BigDecimalTypeConverter {
    @TypeConverter
    fun bigDecimalToString(input: BigDecimal?): String {
        return input?.toPlainString() ?: String.empty()
    }

    @TypeConverter
    fun stringToBigDecimal(input: String?): BigDecimal {
        if (input.isNullOrBlank()) return BigDecimal.ZERO
        return input.toBigDecimalOrNull() ?: BigDecimal.ZERO
    }
}