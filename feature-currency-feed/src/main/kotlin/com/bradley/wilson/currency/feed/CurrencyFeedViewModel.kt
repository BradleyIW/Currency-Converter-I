package com.bradley.wilson.currency.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.currency.CurrencyMapper
import com.bradley.wilson.currency.usecase.ConvertRatesParams
import com.bradley.wilson.currency.usecase.ConvertRatesUseCase
import com.bradley.wilson.currency.usecase.Currency
import com.bradley.wilson.currency.usecase.CurrencyItem
import com.bradley.wilson.currency.usecase.GetLatestRatesParams
import com.bradley.wilson.currency.usecase.GetLatestRatesUseCase
import com.bradley.wilson.network.error.NoConnection
import com.bradley.wilson.network.error.ServerError
import kotlinx.coroutines.Dispatchers

class CurrencyFeedViewModel(
    private val latestRatesUseCase: GetLatestRatesUseCase,
    private val convertRatesUseCase: ConvertRatesUseCase,
    private val currencyMapper: CurrencyMapper
) : ViewModel() {

    private val _currencyRatesFeedLiveData = MutableLiveData<List<CurrencyItem>>()
    val currencyFeedLiveData: LiveData<List<CurrencyItem>> = _currencyRatesFeedLiveData

    init {
        updateFeed(baseCurrency = DEFAULT_BASE_CURRENCY)
    }

    fun updateFeed(baseCurrency: String, amount: Double = DEFAULT_RATE_INPUT) {
        getLatestCurrencyRates(baseCurrency, amount)
    }

    private fun getLatestCurrencyRates(baseCurrency: String, amount: Double) {
        val params = GetLatestRatesParams(baseCurrency)
        latestRatesUseCase.execute(params, viewModelScope, POLLING_INTERVAL_MILLIS) {
            it.fold(::handleFailure) { currencies -> handleFetchSuccess(baseCurrency, currencies, amount) }
        }
    }

    private fun handleFetchSuccess(baseCurrency: String, currencyRates: List<Currency>, amount: Double) {
        convertRatesUseCase.execute(ConvertRatesParams(currencyRates, amount), viewModelScope, Dispatchers.Default) {
            it.fold(::handleFailure) { currencies -> handleConvertSuccess(baseCurrency, currencies, amount) }
        }
    }

    private fun handleConvertSuccess(baseCurrency: String, convertedCurrencies: List<Currency>, amount: Double) {
        val newCurrencies = mutableListOf<CurrencyItem>()
        newCurrencies.add(0, CurrencyItem(baseCurrency, amount))
        newCurrencies.addAll(convertedCurrencies.map { currencyMapper.toCurrencyItem(it) })
        _currencyRatesFeedLiveData.postValue(newCurrencies)
    }

    private fun handleFailure(failure: Failure) {
        val log = when (failure) {
            is NoConnection -> "Connection lost"
            is ServerError -> "Something is wrong with the network, pull down to try again."
            else -> "Unknown error, we're sorry for the inconvenience"
        }
        Log.e(TAG, log)
    }

    companion object {
        private const val TAG = "CurrencyFeedViewModel"
        private const val POLLING_INTERVAL_MILLIS = 1000L
        private const val DEFAULT_BASE_CURRENCY = "EUR"
        private const val DEFAULT_RATE_INPUT = 1.00
    }
}
