package com.bradley.wilson.currency.feed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.bradley.wilson.FunctionalTest
import com.bradley.wilson.R
import org.junit.Before
import org.junit.Test

class CurrencyFeedFragmentTest : FunctionalTest() {

    @Before
    fun setup() {
        launchFragmentInContainer<CurrencyFeedFragment>(
            themeResId = R.style.AppTheme,
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return CurrencyFeedFragment.newInstance()
                }
            }
        )
    }

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
