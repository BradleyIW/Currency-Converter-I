package com.bradley.wilson.currency.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bradley.wilson.R
import com.bradley.wilson.core.database.error.NoResultsError
import com.bradley.wilson.core.exceptions.Failure
import com.bradley.wilson.core.ui.CoroutineDispatcherProvider
import com.bradley.wilson.core.ui.error.ErrorMessage
import com.bradley.wilson.core.ui.state.ItemClicked
import com.bradley.wilson.core.ui.state.ItemDormant
import com.bradley.wilson.core.ui.state.ListItemState
import com.bradley.wilson.core.ui.state.Loaded
import com.bradley.wilson.core.ui.state.Loading
import com.bradley.wilson.core.ui.state.LoadingState
import com.bradley.wilson.core.usecase.CurrencyUseCaseExecutors
import com.bradley.wilson.core.usecase.UseCaseExecutors
import com.bradley.wilson.currency.CurrencyMapper
import com.bradley.wilson.currency.usecase.ConvertRatesParams
import com.bradley.wilson.currency.usecase.ConvertRatesUseCase
import com.bradley.wilson.currency.usecase.Currency
import com.bradley.wilson.currency.usecase.GetLatestRatesParams
import com.bradley.wilson.currency.usecase.GetLatestRatesUseCase
import java.math.BigDecimal

class CurrencyFeedViewModel(
    private val latestRatesUseCase: GetLatestRatesUseCase,
    private val convertRatesUseCase: ConvertRatesUseCase,
    private val currencyMapper: CurrencyMapper,
    private val dispatcherProvider: CoroutineDispatcherProvider
) : ViewModel(), UseCaseExecutors by CurrencyUseCaseExecutors() {

    private var currencyItems: MutableList<CurrencyItem> = mutableListOf()
    private lateinit var baseCurrencyItem: CurrencyItem

    private val _currencyRatesFeedLiveData = MutableLiveData<List<CurrencyItem>>()
    val currencyFeedLiveData: LiveData<List<CurrencyItem>> = _currencyRatesFeedLiveData

    private val _listItemStateLiveData = MutableLiveData<ListItemState>()
    val listItemStateLiveData: LiveData<ListItemState> = _listItemStateLiveData

    private val _noResultsErrorMessageLiveData = MutableLiveData<ErrorMessage>()
    val noResultsErrorMessageLiveData: LiveData<ErrorMessage> = _noResultsErrorMessageLiveData

    private val _loadingIndicatorLiveData = MutableLiveData<LoadingState>()
    val loadingIndicatorLiveData: LiveData<LoadingState> = _loadingIndicatorLiveData

    private var itemState: ListItemState = ItemDormant

    init {
        startFeed()
    }

    fun startFeed() {
        updateLoadingState(Loading)
        updateFeed(CurrencyItem.EMPTY)
    }

    fun onCurrencyItemClicked(updatedItem: CurrencyItem) {
        updateListItemState(ItemClicked)
        updateFeed(updatedItem)
    }

    fun updateFeed(updatedItem: CurrencyItem) {
        baseCurrencyItem = CurrencyItem(updatedItem.country, updatedItem.rate, true)
        latestCurrencyRates(updatedItem.country, updatedItem.rate)
    }

    private fun latestCurrencyRates(baseCurrency: String, amount: BigDecimal) {
        latestRatesUseCase(
            params = GetLatestRatesParams(baseCurrency),
            scope = viewModelScope, dispatcher = dispatcherProvider.io,
            intervalMillis = 1000L
        ) {
            it.fold(::handleFailure) { currencies -> handleFetchSuccess(currencies, amount) }
        }
    }

    private fun handleFetchSuccess(currencyRates: List<Currency>, amount: BigDecimal) {
        val convertCurrencyParams = ConvertRatesParams(currencyRates, amount)
        convertRatesUseCase(
            params = convertCurrencyParams,
            scope = viewModelScope, dispatcher = dispatcherProvider.default
        ) {
            it.fold(::handleFailure, ::handleConvertSuccess)
        }
    }

    private fun handleConvertSuccess(convertedCurrencies: List<Currency>) {
        updateLoadingState(Loaded)
        cleanupAndMapCurrencyItems(convertedCurrencies)
        scrollRecyclerView()
        _currencyRatesFeedLiveData.postValue(currencyItems)
    }

    private fun handleFailure(failure: Failure) {
        updateLoadingState(Loaded)
        when (failure) {
            is NoResultsError -> if (currencyItems.isEmpty()) {
                _noResultsErrorMessageLiveData.postValue(ErrorMessage(R.string.no_overall_results_error_message))
            }
        }
    }

    private fun updateLoadingState(state: LoadingState) =
        _loadingIndicatorLiveData.postValue(state)

    private fun updateListItemState(state: ListItemState) {
        itemState = state
    }

    private fun scrollRecyclerView() {
        _listItemStateLiveData.postValue(itemState)
        updateListItemState(ItemDormant)
    }

    private fun cleanupAndMapCurrencyItems(convertedCurrencies: List<Currency>) {
        currencyItems = convertedCurrencies.map { currencyMapper.toCurrencyItem(it) }
            .toMutableList().apply { add(0, baseCurrencyItem) }
    }
}
