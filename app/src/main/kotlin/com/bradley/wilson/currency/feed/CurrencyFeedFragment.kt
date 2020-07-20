package com.bradley.wilson.currency.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.bradley.wilson.R
import com.bradley.wilson.core.extensions.android.gone
import com.bradley.wilson.core.extensions.android.headingForAccessibility
import com.bradley.wilson.core.extensions.android.scrollToTop
import com.bradley.wilson.core.extensions.android.visible
import com.bradley.wilson.core.idling.GlobalIncrementalIdlingResource
import com.bradley.wilson.core.ui.state.ItemClicked
import com.bradley.wilson.core.ui.state.Loaded
import com.bradley.wilson.core.ui.state.Loading
import com.bradley.wilson.currency.feed.list.CurrencyFeedRecyclerAdapter
import kotlinx.android.synthetic.main.currency_feed_error_content.*
import kotlinx.android.synthetic.main.currency_feed_main_content.*
import kotlinx.android.synthetic.main.fragment_currency_feed.*
import org.koin.android.viewmodel.ext.android.viewModel

class CurrencyFeedFragment : Fragment(R.layout.fragment_currency_feed) {

    private val currencyFeedViewModel: CurrencyFeedViewModel by viewModel()

    private val currencyFeedAdapter by lazy {
        CurrencyFeedRecyclerAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScreenTitle()
        initCurrencyFeed()
        observeFeed()
        observeErrors()
        observeLoadingState()
    }

    private fun initScreenTitle() {
        fragment_currency_feed_title_text_view.headingForAccessibility()
    }

    private fun observeLoadingState() {
        currencyFeedViewModel.loadingIndicatorLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Loading -> {
                    GlobalIncrementalIdlingResource.increment()
                    currency_feed_loading_indicator.visible()
                }
                is Loaded -> {
                    GlobalIncrementalIdlingResource.decrement()
                    currency_feed_loading_indicator.gone()
                }
            }
        }
    }

    private fun observeErrors() {
        currencyFeedViewModel.noResultsErrorMessageLiveData.observe(viewLifecycleOwner) {
            currency_no_results_error.text = getString(it.msg)
        }
    }

    private fun initCurrencyFeed() {
        currency_feed_recycler_view.adapter = currencyFeedAdapter
        with(currencyFeedAdapter) {
            itemClicked {
                currencyFeedViewModel.onCurrencyItemClicked(it)
            }
            rateChanged {
                currencyFeedViewModel.updateFeed(it)
            }
        }
    }

    private fun observeFeed() {
        with(currencyFeedViewModel) {
            listItemStateLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is ItemClicked -> currency_feed_recycler_view.scrollToTop()
                }
            }
            currencyFeedLiveData.observe(viewLifecycleOwner) {
                currencyFeedAdapter.updateList(it)
            }
        }
    }

    companion object {
        fun newInstance() = CurrencyFeedFragment()
    }
}
