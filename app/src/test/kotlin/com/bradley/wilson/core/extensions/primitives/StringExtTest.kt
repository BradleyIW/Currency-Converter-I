package com.bradley.wilson.core.extensions.primitives

import com.bradley.wilson.core.UnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtTest : UnitTest() {

    @Test
    fun `when empty() is called, then assert string is empty`() {
        assertEquals(String.empty(), "")
    }
}
