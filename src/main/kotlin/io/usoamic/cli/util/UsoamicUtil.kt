package io.usoamic.cli.util

import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.usoamickotlin.model.*

fun Idea.toStringIfExist(): String {
    if (isExist) {
        return this.toString()
    }
    else throw ObjectNotFoundException()
}

fun Transaction.toStringIfExist(): String {
    if (isExist) {
        return this.toString()
    }
    else throw ObjectNotFoundException()
}

fun Vote.toStringIfExist(): String {
    if (isExist) {
        return this.toString()
    }
    else throw ObjectNotFoundException()
}

fun Note.toStringIfExist(): String {
    if (isExist) {
        return this.toString()
    }
    else throw ObjectNotFoundException()
}

fun Purchase.toStringIfExist(): String {
    if (isExist) {
        return this.toString()
    }
    else throw ObjectNotFoundException()
}