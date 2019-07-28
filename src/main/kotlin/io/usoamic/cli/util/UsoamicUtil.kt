package io.usoamic.cli.util

import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.usoamickotlin.model.*

fun Idea.printIfExist() {
    if (isExist) {
        println(this)
    }
    else throw ObjectNotFoundException()
}

fun Transaction.printIfExist() {
    if (isExist) {
        println(this)
    }
    else throw ObjectNotFoundException()
}

fun Vote.printIfExist() {
    if (isExist) {
        println(this)
    }
    else throw ObjectNotFoundException()
}

fun Note.printIfExist() {
    if (isExist) {
        println(this)
    }
    else throw ObjectNotFoundException()
}

fun Purchase.printIfExist() {
    if (isExist) {
        println(this)
    }
    else throw ObjectNotFoundException()
}