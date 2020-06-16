@file:Suppress("LeakingThis")

package com.bradley.wilson.core

import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class UnitTest {
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@UnitTest)
}