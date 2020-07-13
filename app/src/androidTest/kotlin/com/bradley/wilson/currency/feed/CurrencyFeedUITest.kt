package com.bradley.wilson.currency.feed

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.bradley.wilson.FunctionalTest
import com.bradley.wilson.R
import org.junit.Rule
import org.junit.Test

class CurrencyFeedUITest : FunctionalTest() {

    @get:Rule
    val activityRule = ActivityTestRule(CurrencyFeedActivity::class.java)

    @Test
    fun givenScreenIsLaunched_thenVerifyCorrectUIElementsAreDisplayed() {
        onView(withText(R.string.fragment_currency_feed_title)).check(matches(isDisplayed()))
        onView(withId(R.id.currency_feed_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun givenScreenIsLaunchedInLandscapeMode_thenVerifyCorrectUIElementsAreDisplayed() = rotateScreen {
        givenScreenIsLaunched_thenVerifyCorrectUIElementsAreDisplayed()
    }
}
