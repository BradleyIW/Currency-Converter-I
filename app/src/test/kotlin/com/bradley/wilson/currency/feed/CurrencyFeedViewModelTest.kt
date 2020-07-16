package com.bradley.wilson.currency.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.database.error.NoResultsError
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.ui.Loading
import com.bradley.wilson.coroutines.CoroutinesTestRule
import com.bradley.wilson.currency.CurrencyMapper
import com.bradley.wilson.currency.usecase.ConvertRatesUseCase
import com.bradley.wilson.currency.usecase.GetLatestRatesParams
import com.bradley.wilson.currency.usecase.GetLatestRatesUseCase
import com.bradley.wilson.livedata.awaitValue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class CurrencyFeedViewModelTest : UnitTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CurrencyFeedViewModel

    @Mock
    private lateinit var latestRatesUseCase: GetLatestRatesUseCase

    @Mock
    private lateinit var convertRatesUseCase: ConvertRatesUseCase

    @Mock
    private lateinit var currencyMapper: CurrencyMapper

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = CurrencyFeedViewModel(latestRatesUseCase, convertRatesUseCase, currencyMapper)
    }

    @Test
    fun `given startFeed is called, then assert LoadingState is loading`() {
        runBlocking {
            viewModel.startFeed()

            assertEquals(viewModel.loadingIndicatorLiveData.awaitValue(), Loading)
        }
    }

    @Test
    fun `given startFeed is called, when latestRatesUseCase fails with NoResultsError, then loading should stop and error message propagated`() {
        runBlocking {
            val initialBaseCurrency = CurrencyItem.EMPTY
            val latestCurrencyRatesParams = GetLatestRatesParams(initialBaseCurrency.country)

            `when`(latestRatesUseCase.run(latestCurrencyRatesParams)).thenReturn(Either.Left(NoResultsError))

            viewModel.startFeed()
        }
    }

    @Test
    fun `given updateFeed is called, when latest rates fails with NoResultsError, then do not propagate no results error`() {

    }
}