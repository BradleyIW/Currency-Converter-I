package com.bradley.wilson.currency.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.currency.CurrencyMapper
import com.bradley.wilson.currency.usecase.ConvertRatesParams
import com.bradley.wilson.currency.usecase.ConvertRatesUseCase
import com.bradley.wilson.currency.usecase.GetLatestRatesParams
import com.bradley.wilson.currency.usecase.GetLatestRatesUseCase
import kotlinx.coroutines.Dispatchers
import java.math.BigDecimal

class CurrencyFeedViewModel(
    private val latestRatesUseCase: GetLatestRatesUseCase,
    private val convertRatesUseCase: ConvertRatesUseCase,
    private val currencyMapper: CurrencyMapper
) : ViewModel() {

    private var currencyItems = mutableListOf<CurrencyItem>()
    private lateinit var baseCurrencyItem: CurrencyItem

    private val _currencyRatesFeedLiveData = MutableLiveData<List<CurrencyItem>>()
    val currencyFeedLiveData: LiveData<List<CurrencyItem>> = _currencyRatesFeedLiveData

    private val _recyclerScrollerLiveData = MutableLiveData<Unit>()
    val recyclerScrollerLiveData: LiveData<Unit> = _recyclerScrollerLiveData

    private var onItemClicked: Boolean = false

    init {
        updateFeed()
    }

    fun onCurrencyItemClicked(baseCurrency: String, amount: BigDecimal) {
        onItemClicked = true
        updateFeed(baseCurrency, amount)
    }

    fun updateFeed(
        baseCurrency: String = DEFAULT_BASE_CURRENCY,
        amount: BigDecimal = BigDecimal(DEFAULT_RATE_INPUT)
    ) {
        baseCurrencyItem = CurrencyItem(baseCurrency, amount, isBateRate = true)
        latestCurrencyRates(baseCurrency, amount)
    }

    private fun latestCurrencyRates(baseCurrency: String, amount: BigDecimal) {
        latestRatesUseCase.execute(GetLatestRatesParams(baseCurrency), viewModelScope, POLLING_INTERVAL_MILLIS) {
            it.fold(::handleFailure) { currencies -> handleFetchSuccess(currencies, amount) }
        }
    }

    private fun handleFetchSuccess(currencyRates: List<Currency>, amount: BigDecimal) {
        convertRatesUseCase.execute(ConvertRatesParams(currencyRates, amount), viewModelScope, Dispatchers.Default) {
            it.fold(::handleFailure, ::handleConvertSuccess)
        }
    }

    private fun handleConvertSuccess(convertedCurrencies: List<Currency>) {
        cleanupAndMapCurrencyItems(convertedCurrencies)
        scrollRecyclerView()
        _currencyRatesFeedLiveData.postValue(currencyItems)
    }

    private fun scrollRecyclerView() {
        if (onItemClicked) {
            _recyclerScrollerLiveData.value = Unit.also {
                onItemClicked = false
            }
        }
    }

    private fun handleFailure(failure: Failure) {
        //Do nothing for now
    }

    private fun cleanupAndMapCurrencyItems(convertedCurrencies: List<Currency>) {
        currencyItems = convertedCurrencies.map { currencyMapper.toCurrencyItem(it) }.toMutableList()
        currencyItems.add(0, baseCurrencyItem)
    }

    companion object {
        private const val POLLING_INTERVAL_MILLIS = 1000L
        private const val DEFAULT_BASE_CURRENCY = "EUR"
        private const val DEFAULT_RATE_INPUT = 1.00
    }
}