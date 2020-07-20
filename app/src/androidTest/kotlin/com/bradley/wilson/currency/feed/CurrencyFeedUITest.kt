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
    fun givenByDefault_conversionForBaseStartsAt1() {
        onView(withId(R.id.currency_feed_recycler_view))
            .check(matches(childMatchesAtPosition(0, hasDescendant(withText("1.00")))))
    }

    @Test
    fun replaceBaseWithZero_hintOnOtherPositionsShouldBeZero() {
        onView(withId(R.id.currency_feed_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>(
                    0,
                    childPerformAction(replaceText("0.00"), R.id.currency_feed_item_view_currency_input)
                )
            )

        //Check early position
        onView(withId(R.id.currency_feed_recycler_view))
            .check(matches(childMatchesAtPosition(1, hasDescendant(withHint("0.00")))))

        //Check middle position
        onView(withId(R.id.currency_feed_recycler_view))
            .perform(scrollToPosition<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>(11))
        onView(withId(R.id.currency_feed_recycler_view))
            .check(matches(childMatchesAtPosition(11, hasDescendant(withHint("0.00")))))

        //Check later position
        onView(withId(R.id.currency_feed_recycler_view))
            .perform(scrollToPosition<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>(21))
        onView(withId(R.id.currency_feed_recycler_view))
            .check(matches(childMatchesAtPosition(21, hasDescendant(withHint("0.00")))))
    }

    @Test
    fun givenEURIsBase_WhenAUDIsClicked_ThenAUDShouldBeAtPositionZero() {
        onView(withId(R.id.currency_feed_recycler_view))
            .check(matches(childMatchesAtPosition(0, hasDescendant(withText("EUR")))))

        onView(withId(R.id.currency_feed_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItem<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>(
                    hasDescendant(
                        withText("AUD")
                    ), click()
                )
            )

        onView(withId(R.id.currency_feed_recycler_view))
            .check(matches(childMatchesAtPosition(0, hasDescendant(withText("AUD")))))
    }

    @Test
    fun givenEURIsBase_WhenUSDIsClicked_ThenUSDShouldBeAtPositionZero() {
        onView(withId(R.id.currency_feed_recycler_view))
            .check(matches(childMatchesAtPosition(0, hasDescendant(withText("EUR")))))

        onView(withId(R.id.currency_feed_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItem<CurrencyFeedRecyclerAdapter.CurrencyFeedViewHolder>(
                    hasDescendant(
                        withText("USD")
                    ), click()
                )
            )

        onView(withId(R.id.currency_feed_recycler_view))
            .check(matches(childMatchesAtPosition(0, hasDescendant(withText("USD")))))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(GlobalIncrementalIdlingResource.countingIdlingResource)
    }
}
