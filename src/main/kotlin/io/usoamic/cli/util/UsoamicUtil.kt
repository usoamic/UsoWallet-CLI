package io.usoamic.cli.util

import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.usoamickt.model.*

fun Idea.toStringIfExist(): String {
    if (isExist) {
        return this.toString()
    }
    throw ObjectNotFoundException()
}

fun Transaction.toStringIfExist(): String {
    if (isExist) {
        return this.toString()
    }
    throw ObjectNotFoundException()
}

fun Vote.toStringIfExist(): String {
    if (isExist) {
        return this.toString()
    }
    throw ObjectNotFoundException()
}

fun Note.toStringIfExist(): String {
    if (isExist) {
        return this.toString()
    }
    throw ObjectNotFoundException()
}

fun Purchase.toStringIfExist(): String {
    if (isExist) {
        return this.toString()
    }
    throw ObjectNotFoundException()
}

fun String.removeQuotes(): String {
    return this.replace("'", "")
}