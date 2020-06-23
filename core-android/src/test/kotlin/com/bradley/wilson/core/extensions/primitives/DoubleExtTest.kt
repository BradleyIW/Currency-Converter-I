package com.bradley.wilson.core.extensions.primitives

import com.bradley.wilson.core.UnitTest
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

class DoubleExtTest : UnitTest() {

    @Test
    fun `given 2 input values, when they are not the same, then equalTo should return false`() {
        val inputA = 1.1
        val inputB = 2.9
        assertFalse(inputA.equalTo(inputB))
    }

    @Test
    fun `given 2 input values, when they not the same, then equalTo should return true`() {
        val inputA = 1.1
        val inputB = 1.1
        assertTrue(inputA.equalTo(inputB))
    }

    @Test
    fun `given 2 input values, when they are not the same, then notEqualTo should return false`() {
        val inputA = 1.1
        val inputB = 1.1
        assertFalse(inputA.notEqualTo(inputB))
    }

    @Test
    fun `given 2 input values, when they not the same, then notEqualTo should return true`() {
        val inputA = 1.1
        val inputB = 1.11
        assertTrue(inputA.notEqualTo(inputB))
    }
}