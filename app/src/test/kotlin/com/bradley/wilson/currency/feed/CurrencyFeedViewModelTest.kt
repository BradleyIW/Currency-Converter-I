package com.bradley.wilson.currency.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bradley.wilson.R
import com.bradley.wilson.core.UnitTest
import com.bradley.wilson.core.database.error.NoResultsError
import com.bradley.wilson.core.extensions.math.equalTo
import com.bradley.wilson.core.functional.Either
import com.bradley.wilson.core.ui.state.ItemClicked
import com.bradley.wilson.core.ui.state.ItemDormant
import com.bradley.wilson.core.ui.state.Loaded
import com.bradley.wilson.core.ui.state.Loading
import com.bradley.wilson.coroutines.CoroutinesTestRule
import com.bradley.wilson.coroutines.TestCoroutineDispatcherProvider
import com.bradley.wilson.currency.CurrencyMapper
import com.bradley.wilson.currency.usecase.ConvertRatesUseCase
import com.bradley.wilson.currency.usecase.GetLatestRatesParams
import com.bradley.wilson.currency.usecase.GetLatestRatesUseCase
import com.bradley.wilson.livedata.awaitValue
import com.bradley.wilson.mockito.any
import com.bradley.wilson.network.error.ServerError
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import java.math.BigDecimal

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
        viewModel = CurrencyFeedViewModel(
            latestRatesUseCase,
            convertRatesUseCase,
            currencyMapper,
            TestCoroutineDispatcherProvider()
        )
    }

    @Test
    fun `given startFeed is called, then assert LoadingState is loading`() {
        runBlocking {
            viewModel.startFeed()
            assertEquals(viewModel.loadingIndicatorLiveData.awaitValue(), Loading)
        }
    }

    @Test
    fun `given startFeed, when latestRatesUseCase fails with NoResultsError, then loading should stop and error message propagated`() {
        runBlocking {
            val initialBaseCurrency = CurrencyItem.EMPTY
            val latestCurrencyRatesParams = GetLatestRatesParams(initialBaseCurrency.country)

            `when`(latestRatesUseCase.run(latestCurrencyRatesParams)).thenReturn(Either.Left(NoResultsError))

            viewModel.startFeed()

            assertEquals(viewModel.loadingIndicatorLiveData.awaitValue(), Loaded)
            assertEquals(
                viewModel.noResultsErrorMessageLiveData.awaitValue().msg,
                R.string.no_overall_results_error_message
            )
        }
    }

    @Test
    fun `given startFeed is called, when latestRatesUseCase succeeds and conversion fails, then loading state should change to loaded`() {
        runBlocking {
            val currencies = listOf(Currency(TEST_BASE_CURRENCY_COUNTRY_CODE, BigDecimal.TEN, 0L))
            `when`(latestRatesUseCase.run(any())).thenReturn(Either.Right(currencies))
            `when`(convertRatesUseCase.run(any())).thenReturn(Either.Left(ServerError))

            viewModel.startFeed()

            assertEquals(viewModel.loadingIndicatorLiveData.awaitValue(), Loaded)
        }
    }

    @Test
    fun `given startFeed is called, when latestRatesUseCase succeeds and conversion succeeds, then loading state should change to loaded`() {
        runBlocking {
            val currencies = listOf(Currency(UNITED_STATES_DOLLAR_CURRENCY_CODE, BigDecimal.TEN, 0L))
            `when`(latestRatesUseCase.run(any())).thenReturn(Either.Right(currencies))
            `when`(convertRatesUseCase.run(any())).thenReturn(Either.Right(currencies))

            viewModel.startFeed()

            //Loading
            assertEquals(viewModel.loadingIndicatorLiveData.awaitValue(), Loaded)
        }
    }

    @Test
    fun `given startFeed is called, when latestRatesUseCase succeeds and conversion succeeds, then list item state should be dormant`() {
        runBlocking {
            val currencies = listOf(Currency(UNITED_STATES_DOLLAR_CURRENCY_CODE, BigDecimal.TEN, 0L))
            `when`(latestRatesUseCase.run(any())).thenReturn(Either.Right(currencies))
            `when`(convertRatesUseCase.run(any())).thenReturn(Either.Right(currencies))

            viewModel.startFeed()

            //Item state
            assertEquals(viewModel.listItemStateLiveData.awaitValue(), ItemDormant)
        }
    }

    @Test
    fun `given startFeed is called, when latestRatesUseCase succeeds and conversion succeeds, then assert base and converted currencies`() {
        runBlocking {
            val currency = Currency(UNITED_STATES_DOLLAR_CURRENCY_CODE, BigDecimal.TEN, 0L)
            val currencies = listOf(currency)
            `when`(latestRatesUseCase.run(any())).thenReturn(Either.Right(currencies))
            `when`(convertRatesUseCase.run(any())).thenReturn(Either.Right(currencies))

            viewModel.startFeed()

            //Base currency
            val currencyItems = viewModel.currencyFeedLiveData.awaitValue()
            assertBaseCurrencyValues(currencyItems, CurrencyItem.EMPTY)

            //Converted item
            assertEquals(currencyItems[1], currencyMapper.toCurrencyItem(currency))
        }
    }

    @Test
    fun `given onItemClicked is called, when latestRatesUseCase succeeds and conversion fails, then loading state should change to loaded`() {
        runBlocking {
            val currencies = listOf(Currency(TEST_BASE_CURRENCY_COUNTRY_CODE, BigDecimal.TEN, 0L))
            `when`(latestRatesUseCase.run(any())).thenReturn(Either.Right(currencies))
            `when`(convertRatesUseCase.run(any())).thenReturn(Either.Left(ServerError))

            viewModel.onCurrencyItemClicked(CurrencyItem.EMPTY)

            assertEquals(viewModel.loadingIndicatorLiveData.awaitValue(), Loaded)
        }
    }

    @Test
    fun `given onItemClicked is called, when latestRatesUseCase succeeds and conversion succeeds, then loading state should change to loaded`() {
        runBlocking {
            val currencies = listOf(Currency(UNITED_STATES_DOLLAR_CURRENCY_CODE, BigDecimal.TEN, 0L))
            `when`(latestRatesUseCase.run(any())).thenReturn(Either.Right(currencies))
            `when`(convertRatesUseCase.run(any())).thenReturn(Either.Right(currencies))

            viewModel.onCurrencyItemClicked(CurrencyItem.EMPTY)

            //Loading
            assertEquals(viewModel.loadingIndicatorLiveData.awaitValue(), Loaded)
        }
    }

    @Test
    fun `given onItemClicked is called, when latestRatesUseCase succeeds and conversion succeeds, then list item state should be clicked`() {
        runBlocking {
            val currencies = listOf(Currency(UNITED_STATES_DOLLAR_CURRENCY_CODE, BigDecimal.TEN, 0L))
            `when`(latestRatesUseCase.run(any())).thenReturn(Either.Right(currencies))
            `when`(convertRatesUseCase.run(any())).thenReturn(Either.Right(currencies))

            viewModel.onCurrencyItemClicked(CurrencyItem.EMPTY)

            //Item state
            assertEquals(viewModel.listItemStateLiveData.awaitValue(), ItemClicked)
        }
    }

    @Test
    fun `given onItemClicked is called, when latestRatesUseCase succeeds and conversion succeeds, then assert base and converted currencies`() {
        runBlocking {
            val newBaseCurrency = CurrencyItem(SWISS_FRANK_CURRENCY_CODE, BigDecimal.TEN, true, 0L)
            val currency = Currency(UNITED_STATES_DOLLAR_CURRENCY_CODE, BigDecimal.TEN, 0L)
            val currencies = listOf(currency)
            `when`(latestRatesUseCase.run(any())).thenReturn(Either.Right(currencies))
            `when`(convertRatesUseCase.run(any())).thenReturn(Either.Right(currencies))

            viewModel.onCurrencyItemClicked(newBaseCurrency)

            //Base currency
            val currencyItems = viewModel.currencyFeedLiveData.awaitValue()
            assertBaseCurrencyValues(currencyItems, newBaseCurrency)

            //Converted item
            assertEquals(currencyItems[1], currencyMapper.toCurrencyItem(currency))
        }
    }

    private fun assertBaseCurrencyValues(convertedItems: List<CurrencyItem>, baseCurrencyItem: CurrencyItem) {
        assertEquals(convertedItems.first().country, baseCurrencyItem.country)
        assertTrue(convertedItems.first().isBateRate)
    }

    @After
    fun tearDown() {
        viewModel.cancelJobs()
    }

    companion object {
        private const val UNITED_STATES_DOLLAR_CURRENCY_CODE = "USD"
        private const val SWISS_FRANK_CURRENCY_CODE = "CHF"
        private const val TEST_BASE_CURRENCY_COUNTRY_CODE = "EUR"
    }
}
