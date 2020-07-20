package com.bradley.wilson.testing.mockito

import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

object InjectMocksRule {
    fun create(testClass: Any) = TestRule { statement, _ ->
        MockitoAnnotations.initMocks(testClass)
        statement
    }
}
