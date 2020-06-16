package com.bradley.wilson.currency.data.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("baseCurrency")
    @Expose
    val base: String,

    @SerializedName("rates")
    @Expose
    val rates: Map<String, Double>
)