package io.usoamic.cli.util

import java.math.BigDecimal

fun BigDecimal.toReadableString(): String {
    return String.format("%.0f\n", this)
}