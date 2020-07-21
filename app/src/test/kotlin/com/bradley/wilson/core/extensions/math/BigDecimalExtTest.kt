package com.bradley.wilson.core.extensions.math

import com.bradley.wilson.testing.UnitTest
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.math.BigDecimal

class BigDecimalExtTest : UnitTest() {

    @Test
    fun `given ZERO BigDecimal, assert equalsZero returns true`() {
        var input = BigDecimal(0.000)
        assertTrue(input.equalsZero())

        input = BigDecimal.ZERO
        assertTrue(input.equalsZero())
    }

    @Test
    fun `given two identical BigDecimal values, assert equalTo returns true`() {
        val inputOne = BigDecimal(21.76477748994000)
        val inputTwo = BigDecimal(21.76477748994000)
        assertTrue(inputOne.equalTo(inputTwo))
    }

    @Test
    fun `given two different BigDecimal values, assert notEqualTo returns true`() {
        val inputOne = BigDecimal(21.76477748994000)
        val inputTwo = BigDecimal(21.76477748994001)
        assertTrue(inputOne.notEqualTo(inputTwo))
    }
}