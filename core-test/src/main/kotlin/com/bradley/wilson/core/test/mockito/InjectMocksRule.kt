package com.bradley.wilson.core.test.mockito

import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

class InjectMocksRule private constructor() {
    companion object {
        fun create(testClass: Any) = TestRule { statement, _ ->
            MockitoAnnotations.initMocks(testClass)
            statement
        }
    }
}