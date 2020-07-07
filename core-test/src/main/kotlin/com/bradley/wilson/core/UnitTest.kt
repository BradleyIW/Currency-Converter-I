@file:Suppress("LeakingThis")

package com.bradley.wilson.core

import com.bradley.wilson.core.mockito.InjectMocksRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open class UnitTest {

    @get:Rule
    val injectMocks = InjectMocksRule.create(this@UnitTest)
}
