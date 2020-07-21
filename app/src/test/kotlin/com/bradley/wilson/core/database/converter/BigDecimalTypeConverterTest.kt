package com.bradley.wilson.core.database.converter

import com.bradley.wilson.testing.UnitTest
import com.bradley.wilson.core.extensions.math.equalTo
import com.bradley.wilson.core.extensions.primitives.empty
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class BigDecimalTypeConverterTest : UnitTest() {

    private lateinit var converter: BigDecimalTypeConverter

    @Before
    fun setup() {
        converter = BigDecimalTypeConverter()
    }

    @Test
    fun `given bigDecimalToString is called, when input is null, then return empty string`() {
        val output = converter.bigDecimalToString(null)
        assertEquals(output, String.empty())
    }

    @Test
    fun `given bigDecimalToString is called, when input is valid, then return plain string`() {
        val input = BigDecimal(1.23455)
        val output = converter.bigDecimalToString(input)
        assertEquals(output, input.toPlainString())
    }

    @Test
    fun `given stringToBigDecimal is called, when input is null, then return ZERO`() {
        val output = converter.stringToBigDecimal(null)
        assertTrue(output.equalTo(BigDecimal.ZERO))
    }

    @Test
    fun `given stringToBigDecimal is called, when input is empty, then return ZERO`() {
        val output = converter.stringToBigDecimal(String.empty())
        assertTrue(output.equalTo(BigDecimal.ZERO))
    }

    @Test
    fun `given bigDecimalToString is called, when input is valid, then return big decimal`() {
        val input = "1.235446"
        val output = converter.stringToBigDecimal(input)
        assertEquals(output, input.toBigDecimal())
    }
}
