package com.bradley.wilson.testing

import android.app.Activity
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.runner.RunWith

@Suppress("UnnecessaryAbstractClass")
@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class FunctionalTest {

    val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun rotateScreen(block: () -> Unit) = with(uiDevice) {
        setOrientationLeft()
        block()
        setOrientationNatural()
    }
}

abstract class ActivityTest(clazz: Class<out Activity>) : FunctionalTest() {

    @get:Rule
    val activityRule = ActivityTestRule(clazz)

    companion object {
        @BeforeClass
        @JvmStatic
        fun enableAllChecks() {
            AccessibilityChecks.enable()
                .setRunChecksFromRootView(true)
                .setThrowExceptionForErrors(false)
        }
    }
}
