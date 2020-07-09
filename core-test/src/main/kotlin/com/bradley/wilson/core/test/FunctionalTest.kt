package com.bradley.wilson.core.test

import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.BeforeClass
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
open class FunctionalTest {

    val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun rotateScreen(block: () -> Unit) = with(uiDevice) {
        setOrientationLeft()
        block()
        setOrientationNatural()
    }

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