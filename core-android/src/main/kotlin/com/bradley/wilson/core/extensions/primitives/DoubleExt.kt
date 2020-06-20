package com.bradley.wilson.core.extensions.primitives

import kotlin.math.abs

infix fun Double.equalTo(other: Double) = abs(this - other) < 0.000001

infix fun Double.notEqualTo(other: Double) = !equalTo(other)
