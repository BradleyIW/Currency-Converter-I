package com.bradley.wilson.currency.feed

import com.bradley.wilson.testing.UnitTest
import com.bradley.wilson.core.extensions.math.equalTo
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.math.BigDecimal

class CurrencyItemTest : UnitTest() {

    @Test
    fun `given CurrencyItem is EMPTY, then assert default values are correct`() {
        val currency = CurrencyItem.EMPTY

        assertEquals(currency.country, "EUR")
        assertFalse(currency.isBateRate)
        assertTrue(currency.rate.equalTo(BigDecimal.ONE))
    }
}
