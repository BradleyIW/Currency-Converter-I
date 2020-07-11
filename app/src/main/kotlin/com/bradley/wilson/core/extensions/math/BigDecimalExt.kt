package com.bradley.wilson.core.extensions.math

import java.math.BigDecimal

fun BigDecimal.equalsZero(): Boolean = this.compareTo(BigDecimal.ZERO) == 0

fun BigDecimal.equalTo(compareTo: BigDecimal): Boolean = this.compareTo(compareTo) == 0

fun BigDecimal.notEqualTo(compareTo: BigDecimal): Boolean = !equalTo(compareTo)
