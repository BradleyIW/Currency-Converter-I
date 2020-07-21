package com.bradley.wilson.core.database.converter

import androidx.room.TypeConverter
import com.bradley.wilson.core.extensions.primitives.empty
import java.math.BigDecimal

class BigDecimalTypeConverter {
    @TypeConverter
    fun bigDecimalToString(input: BigDecimal?): String =
        input?.toPlainString() ?: String.empty()

    @TypeConverter
    fun stringToBigDecimal(input: String?): BigDecimal {
        if (input.isNullOrBlank()) return BigDecimal.ZERO
        return input.toBigDecimal()
    }
}
