package com.bradley.wilson.currency.feed.flags

import java.util.Currency

class CurrencyFlags private constructor() {

    companion object {

        private const val UNKNOWN_COUNTRY_CODE = "UKN"

        private val FLAGS = mutableMapOf(
            Pair("EUR", "\uD83C\uDDEA\uD83C\uDDFA"),
            Pair("AUD", "\uD83C\uDDE6\uD83C\uDDFA"),
            Pair("BGN", "\uD83C\uDDE7\uD83C\uDDEC"),
            Pair("BRL", "\uD83C\uDDE7\uD83C\uDDF7"),
            Pair("CAD", "\uD83C\uDDE8\uD83C\uDDE6"),
            Pair("CHF", "\uD83C\uDDE8\uD83C\uDDED"),
            Pair("CNY", "\uD83C\uDDE8\uD83C\uDDF3"),
            Pair("CZK", "\uD83C\uDDE8\uD83C\uDDFF"),
            Pair("DKK", "\uD83C\uDDE9\uD83C\uDDF0"),
            Pair("GBP", "\uD83C\uDDEC\uD83C\uDDE7"),
            Pair("HKD", "\uD83C\uDDED\uD83C\uDDF0"),
            Pair("HRK", "\uD83C\uDDED\uD83C\uDDF7"),
            Pair("HUF", "\uD83C\uDDED\uD83C\uDDFA"),
            Pair("IDR", "\uD83C\uDDEE\uD83C\uDDE9"),
            Pair("ILS", "\uD83C\uDDEE\uD83C\uDDF1"),
            Pair("INR", "\uD83C\uDDEE\uD83C\uDDF3"),
            Pair("ISK", "\uD83C\uDDEE\uD83C\uDDF8"),
            Pair("JPY", "\uD83C\uDDEF\uD83C\uDDF5"),
            Pair("KRW", "\uD83C\uDDF0\uD83C\uDDF7"),
            Pair("MXN", "\uD83C\uDDF2\uD83C\uDDFD"),
            Pair("MYR", "\uD83C\uDDF2\uD83C\uDDFE"),
            Pair("NOK", "\uD83C\uDDF3\uD83C\uDDF4"),
            Pair("NZD", "\uD83C\uDDF3\uD83C\uDDFF"),
            Pair("PHP", "\uD83C\uDDF5\uD83C\uDDED"),
            Pair("PLN", "\uD83C\uDDF5\uD83C\uDDF1"),
            Pair("RON", "\uD83C\uDDF7\uD83C\uDDF4"),
            Pair("RUB", "\uD83C\uDDF7\uD83C\uDDFA"),
            Pair("SEK", "\uD83C\uDDF8\uD83C\uDDEA"),
            Pair("SGD", "\uD83C\uDDF8\uD83C\uDDEC"),
            Pair("THB", "\uD83C\uDDF9\uD83C\uDDED"),
            Pair("USD", "\uD83C\uDDFA\uD83C\uDDF2"),
            Pair("ZAR", "\uD83C\uDDFF\uD83C\uDDE6"),

            //Generic currency exchange emoji for unknown currencies
            Pair(UNKNOWN_COUNTRY_CODE, "\uD83D\uDCB1\n")
        )

        fun getFlagEmojiForCurrency(currency: Currency?): String? =
            currency?.let { FLAGS[it.currencyCode] ?: FLAGS[UNKNOWN_COUNTRY_CODE] }
    }

}
