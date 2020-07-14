package com.bradley.wilson.currency.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bradley.wilson.R
import com.bradley.wilson.core.database.error.NoResultsError
import com.bradley.wilson.core.error.ErrorMessage
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

    private val _noResultsErrorMessageLiveData = MutableLiveData<ErrorMessage>()
    val noResultsErrorMessageLiveData: LiveData<ErrorMessage> = _noResultsErrorMessageLiveData

    private var onItemClicked: Boolean = false

    init {
        updateFeed(CurrencyItem.EMPTY)
    }

    fun onCurrencyItemClicked(updatedItem: CurrencyItem) {
        onItemClicked = true
        updateFeed(updatedItem)
    }

    fun updateFeed(updatedItem: CurrencyItem) {
        baseCurrencyItem = CurrencyItem(updatedItem.country, updatedItem.rate, true, updatedItem.lastUpdatedAt)
        latestCurrencyRates(updatedItem.country, updatedItem.rate)
    }

    private fun latestCurrencyRates(baseCurrency: String, amount: BigDecimal) {
        latestRatesUseCase.execute(GetLatestRatesParams(baseCurrency), viewModelScope) {
            it.fold(::handleFailure) { currencies -> handleFetchSuccess(currencies, amount) }
        }
    }

    private fun handleFetchSuccess(currencyRates: List<Currency>, amount: BigDecimal) {
        val convertCurrencyParams = ConvertRatesParams(currencyRates, amount)
        convertRatesUseCase.execute(convertCurrencyParams, viewModelScope, Dispatchers.Default) {
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
        when (failure) {
            is NoResultsError -> if (currencyItems.isEmpty()) {
                _noResultsErrorMessageLiveData.postValue(ErrorMessage(R.string.no_overall_results_error_message))
            }
        }
    }

    private fun cleanupAndMapCurrencyItems(convertedCurrencies: List<Currency>) {
        currencyItems = convertedCurrencies.map {
            currencyMapper.toCurrencyItem(it)
        }.toMutableList()
        currencyItems.add(0, baseCurrencyItem)
    }
}
