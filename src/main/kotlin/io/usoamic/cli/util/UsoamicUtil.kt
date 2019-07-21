package io.usoamic.cli.util

import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.usoamickotlin.model.Idea
import io.usoamic.usoamickotlin.model.Note
import io.usoamic.usoamickotlin.model.Transaction
import io.usoamic.usoamickotlin.model.Vote

fun Idea.printIfExist() {
    if (isExist) {
        println(this)
    }
    throw ObjectNotFoundException()
}

fun Transaction.printIfExist() {
    if (isExist) {
        println(this)
    }
    throw ObjectNotFoundException()
}

fun Vote.printIfExist() {
    if (isExist) {
        println(this)
    }
    throw ObjectNotFoundException()
}

fun Note.printIfExist() {
    if (isExist) {
        println(this)
    }
    throw ObjectNotFoundException()
}
