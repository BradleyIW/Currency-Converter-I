package com.bradley.wilson.core.extensions.primitives

import kotlin.math.abs

const val DOUBLE_EQUAL_DIFF = 0.000001

infix fun Double.equalTo(other: Double) = abs(this - other) < DOUBLE_EQUAL_DIFF

infix fun Double.notEqualTo(other: Double) = !equalTo(other)
