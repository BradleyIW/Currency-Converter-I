package com.bradley.wilson.currency.feed

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.bradley.wilson.ActivityTest
import com.bradley.wilson.R
import com.bradley.wilson.core.idling.GlobalIncrementalIdlingResource
import com.bradley.wilson.currency.RecyclerActions.childMatchesAtPosition
import com.bradley.wilson.currency.RecyclerActions.childPerformAction
import com.bradley.wilson.currency.feed.list.CurrencyFeedRecyclerAdapter
import org.junit.After
import org.junit.Before
import org.junit.Test

class CurrencyFeedUITest : ActivityTest(CurrencyFeedActivity::class.java) {

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(GlobalIncrementalIdlingResource.countingIdlingResource)
    }

    @Test
    fun givenScreenIsLaunched_thenVerifyCorrectUIElementsAreDisplayed() {
        onView(withText(R.string.fragment_currency_feed_title)).check(matches(isDisplayed()))
        onView(withId(R.id.currency_feed_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun givenScreenIsLaunchedAndRotated_thenVerifyCorrectUIElementsAreDisplayed() = rotateScreen {
        givenScreenIsLaunched_thenVerifyCorrectUIElementsAreDisplayed()
    }

    @Test
    fun replaceBaseWithZero_hintOnOtherPositionsShouldBeZero() {
        replaceCurrencyInput(input = ZERO_INPUT)

        //Check early position
        scrollAndCheckPositionForHint(1)

        //Check middle position
        scrollAndCheckPositionForHint(11)

        //Check later position
        scrollAndCheckPositionForHint(21)
    }

    @Test
    fun givenEURIsBase_WhenAUDIsClicked_ThenAUDShouldBeAtPositionZero() {
        checkDescendantTextAtPosition(text = EURO_CURRENCY_CODE)

        onView(withId(R.id.currency_feed_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItem<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>(
                    hasDescendant(
                        withText(AUD_CURRENCY_CODE)
                    ), click()
                )
            )

        checkDescendantTextAtPosition(text = AUD_CURRENCY_CODE)
    }

    @Test
    fun givenEURIsBase_WhenUSDIsClicked_ThenUSDShouldBeAtPositionZero() {
        checkDescendantTextAtPosition(text = EURO_CURRENCY_CODE)

        onView(withId(R.id.currency_feed_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItem<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>(
                    hasDescendant(
                        withText(USD_CURRENCY_CODE)
                    ), click()
                )
            )

        checkDescendantTextAtPosition(text = USD_CURRENCY_CODE)
    }

    @Test
    fun inputValueShouldNotChangeWhenScreenIsRotated() {
        replaceCurrencyInput(input = VALID_INPUT)

        uiDevice.setOrientationLeft()
        uiDevice.setOrientationNatural()

        checkDescendantTextAtPosition(text = VALID_INPUT)
    }

    private fun replaceCurrencyInput(position: Int = 0, input: String) {
        onView(withId(R.id.currency_feed_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>(
                    position,
                    childPerformAction(replaceText(input), R.id.currency_feed_item_view_currency_input)
                )
            )
    }

    private fun checkDescendantTextAtPosition(position: Int = 0, text: String) {
        onView(withId(R.id.currency_feed_recycler_view))
            .check(matches(childMatchesAtPosition(position, hasDescendant(withText(text)))))
    }

    private fun scrollAndCheckPositionForHint(position: Int, input: String = ZERO_INPUT) {
        onView(withId(R.id.currency_feed_recycler_view))
            .perform(scrollToPosition<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>(position))

        onView(withId(R.id.currency_feed_recycler_view))
            .check(matches(childMatchesAtPosition(position, hasDescendant(withHint(input)))))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(GlobalIncrementalIdlingResource.countingIdlingResource)
    }

    companion object {
        private const val VALID_INPUT = "6,00"
        private const val ZERO_INPUT = "0.00"
        private const val USD_CURRENCY_CODE = "USD"
        private const val EURO_CURRENCY_CODE = "EUR"
        private const val AUD_CURRENCY_CODE = "AUD"
    }
}
