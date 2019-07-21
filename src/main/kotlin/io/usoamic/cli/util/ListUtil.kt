package io.usoamic.cli.util

fun List<String>.getOrEmpty(index: Int): String {
    if (index in 0..lastIndex) {
        return get(index)
    }
    return ""
}
