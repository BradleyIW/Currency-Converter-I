package com.bradley.wilson.currency.feed

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.bradley.wilson.core.test.FunctionalTest
import com.bradley.wilson.core.test.device.AirplaneMode
import com.bradley.wilson.currency.R
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test

class CurrencyFeedActivityTest : FunctionalTest() {

    @get:Rule
    val activityRule = ActivityTestRule(CurrencyFeedActivity::class.java, false, true)

    @Test
    fun givenScreenIsLaunched_thenVerifyCorrectUIElementsAreDisplayed() {
        onView(withText(R.string.fragment_currency_feed_title)).check(matches(isDisplayed()))
        onView(withId(R.id.currency_feed_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun givenScreenIsLaunched_whenAirplaneModeIsOn_thenVerifyCorrectUIElementsAreDisplayed() {
        onView(withText(R.string.fragment_currency_feed_title)).check(matches(isDisplayed()))
        onView(withId(R.id.currency_feed_recycler_view)).check(matches(isDisplayed()))

        AirplaneMode.setAirplaneMode(true)

        onView(withText(R.string.no_connection_error_message)).check(matches(isDisplayed()))

        AirplaneMode.setAirplaneMode(false)

        onView(withText(R.string.no_connection_error_message)).check(matches(allOf(not(isDisplayed()))))
    }

    @Test
    fun givenScreenIsLaunchedInLandscapeMode_thenVerifyCorrectUIElementsAreDisplayed() = rotateScreen {
        givenScreenIsLaunched_thenVerifyCorrectUIElementsAreDisplayed()
    }

}