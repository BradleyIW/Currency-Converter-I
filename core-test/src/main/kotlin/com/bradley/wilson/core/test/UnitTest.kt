@file:Suppress("LeakingThis")

package com.bradley.wilson.core.test

import com.bradley.wilson.core.test.mockito.InjectMocksRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open class UnitTest {

    @get:Rule
    val injectMocks = InjectMocksRule.create(this@UnitTest)
}
