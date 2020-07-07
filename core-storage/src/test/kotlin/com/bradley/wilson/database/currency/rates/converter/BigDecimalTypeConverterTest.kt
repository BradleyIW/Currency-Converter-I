package com.bradley.wilson.database.currency.rates.converter

import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.extensions.math.equalTo
import com.bradley.wilson.core.extensions.math.equalsZero
import com.bradley.wilson.core.extensions.primitives.empty
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class BigDecimalTypeConverterTest : UnitTest() {

    private lateinit var bigDecimalTypeConverter: BigDecimalTypeConverter

    @Before
    fun setup() {
        bigDecimalTypeConverter = BigDecimalTypeConverter()
    }

    @Test
    fun `given a valid BigDecimal input value, when bigDecimalToString is called, then assert output value as string`() {
        val input = BigDecimal.TEN

        val output = bigDecimalTypeConverter.bigDecimalToString(input)
        assertEquals(input.toPlainString(), output)
    }

    @Test
    fun `given a valid String input value, when stringToBigDecimal is called, then assert output is valid BigDecimal`() {
        val input = "13.234557747884775848"
        val output = bigDecimalTypeConverter.stringToBigDecimal(input)

        assertTrue(input.toBigDecimal().equalTo(output))
    }

    @Test
    fun `given a null BigDecimal input value, when bigDecimalToString is called, then assert output is empty string`() {
        val output = bigDecimalTypeConverter.bigDecimalToString(null)
        assertEquals(String.empty(), output)
    }

    @Test
    fun `given a null String input value, when stringToBigDecimal is called, then assert output is ZERO`() {
        val output = bigDecimalTypeConverter.stringToBigDecimal(null)
        assertTrue(output.equalsZero())
    }

    @Test
    fun `given a blank String input value, when stringToBigDecimal is called, then assert output is ZERO`() {
        val output = bigDecimalTypeConverter.stringToBigDecimal(String.empty())
        assertTrue(output.equalsZero())
    }

    @Test
    fun `given a invalid String input value, when stringToBigDecimal is called, then assert output is ZERO`() {
        val input = "BigDecimalCannotParseMe"

        val output = bigDecimalTypeConverter.stringToBigDecimal(input)
        assertTrue(output.equalsZero())
    }
}