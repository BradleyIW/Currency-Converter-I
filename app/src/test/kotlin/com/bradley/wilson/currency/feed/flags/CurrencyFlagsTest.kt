package com.bradley.wilson.currency.feed.flags

import com.bradley.wilson.testing.UnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyFlagsTest : UnitTest() {

    @Test
    fun `given EUR flag is requested, then return EUR flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("EUR")
        assertEquals(flag, "\uD83C\uDDEA\uD83C\uDDFA")
    }

    @Test
    fun `given AUD flag is requested, then return AUD flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("AUD")
        assertEquals(flag, "\uD83C\uDDE6\uD83C\uDDFA")
    }

    @Test
    fun `given BGN flag is requested, then return BGN flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("BGN")
        assertEquals(flag, "\uD83C\uDDE7\uD83C\uDDEC")
    }

    @Test
    fun `given BRL flag is requested, then return BRL flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("BRL")
        assertEquals(flag, "\uD83C\uDDE7\uD83C\uDDF7")
    }

    @Test
    fun `given CAD flag is requested, then return CAD flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("CAD")
        assertEquals(flag, "\uD83C\uDDE8\uD83C\uDDE6")
    }

    @Test
    fun `given CHF flag is requested, then return CHF flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("CHF")
        assertEquals(flag, "\uD83C\uDDE8\uD83C\uDDED")
    }

    @Test
    fun `given CNY flag is requested, then return CNY flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("CNY")
        assertEquals(flag, "\uD83C\uDDE8\uD83C\uDDF3")
    }

    @Test
    fun `given CZK flag is requested, then return CZK flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("CZK")
        assertEquals(flag, "\uD83C\uDDE8\uD83C\uDDFF")
    }

    @Test
    fun `given DKK flag is requested, then return DKK flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("DKK")
        assertEquals(flag, "\uD83C\uDDE9\uD83C\uDDF0")
    }

    @Test
    fun `given GBP flag is requested, then return GBP flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("GBP")
        assertEquals(flag, "\uD83C\uDDEC\uD83C\uDDE7")
    }

    @Test
    fun `given HKD flag is requested, then return HKD flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("HKD")
        assertEquals(flag, "\uD83C\uDDED\uD83C\uDDF0")
    }

    @Test
    fun `given HRK flag is requested, then return HRK flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("HRK")
        assertEquals(flag, "\uD83C\uDDED\uD83C\uDDF7")
    }

    @Test
    fun `given HUF flag is requested, then return HUF flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("HUF")
        assertEquals(flag, "\uD83C\uDDED\uD83C\uDDFA")
    }

    @Test
    fun `given IDR flag is requested, then return IDR flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("IDR")
        assertEquals(flag, "\uD83C\uDDEE\uD83C\uDDE9")
    }

    @Test
    fun `given ILS flag is requested, then return ILS flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("ILS")
        assertEquals(flag, "\uD83C\uDDEE\uD83C\uDDF1")
    }

    @Test
    fun `given INR flag is requested, then return INR flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("INR")
        assertEquals(flag, "\uD83C\uDDEE\uD83C\uDDF3")
    }

    @Test
    fun `given ISK flag is requested, then return ISK flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("ISK")
        assertEquals(flag, "\uD83C\uDDEE\uD83C\uDDF8")
    }

    @Test
    fun `given JPY flag is requested, then return JPY flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("JPY")
        assertEquals(flag, "\uD83C\uDDEF\uD83C\uDDF5")
    }

    @Test
    fun `given KRW flag is requested, then return KRW flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("KRW")
        assertEquals(flag, "\uD83C\uDDF0\uD83C\uDDF7")
    }

    @Test
    fun `given MXN flag is requested, then return MXN flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("MXN")
        assertEquals(flag, "\uD83C\uDDF2\uD83C\uDDFD")
    }

    @Test
    fun `given MYR flag is requested, then return MYR flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("MYR")
        assertEquals(flag, "\uD83C\uDDF2\uD83C\uDDFE")
    }

    @Test
    fun `given NOK flag is requested, then return NOK flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("NOK")
        assertEquals(flag, "\uD83C\uDDF3\uD83C\uDDF4")
    }

    @Test
    fun `given NZD flag is requested, then return NZD flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("NZD")
        assertEquals(flag, "\uD83C\uDDF3\uD83C\uDDFF")
    }

    @Test
    fun `given PHP flag is requested, then return PHP flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("PHP")
        assertEquals(flag, "\uD83C\uDDF5\uD83C\uDDED")
    }

    @Test
    fun `given PLN flag is requested, then return PLN flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("PLN")
        assertEquals(flag, "\uD83C\uDDF5\uD83C\uDDF1")
    }

    @Test
    fun `given RON flag is requested, then return RON flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("RON")
        assertEquals(flag, "\uD83C\uDDF7\uD83C\uDDF4")
    }

    @Test
    fun `given RUB flag is requested, then return RUB flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("RUB")
        assertEquals(flag, "\uD83C\uDDF7\uD83C\uDDFA")
    }

    @Test
    fun `given SEK flag is requested, then return SEK flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("SEK")
        assertEquals(flag, "\uD83C\uDDF8\uD83C\uDDEA")
    }

    @Test
    fun `given SGD flag is requested, then return SGD flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("SGD")
        assertEquals(flag, "\uD83C\uDDF8\uD83C\uDDEC")
    }

    @Test
    fun `given THB flag is requested, then return THB flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("THB")
        assertEquals(flag, "\uD83C\uDDF9\uD83C\uDDED")
    }

    @Test
    fun `given USD flag is requested, then return USD flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("USD")
        assertEquals(flag, "\uD83C\uDDFA\uD83C\uDDF2")
    }

    @Test
    fun `given ZAR flag is requested, then return ZAR flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency("ZAR")
        assertEquals(flag, "\uD83C\uDDFF\uD83C\uDDE6")
    }

    @Test
    fun `given null flag is requested, then return unknown flag in unicode`() {
        val flag = CurrencyFlags.getFlagEmojiForCurrency(null)
        assertEquals(flag, "\uD83D\uDCB1\n")
    }
}
