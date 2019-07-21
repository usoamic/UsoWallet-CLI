package io.usoamic.cli.util

import io.usoamic.usoamickotlin.model.Idea
import io.usoamic.usoamickotlin.model.Note
import io.usoamic.usoamickotlin.model.Transaction
import io.usoamic.usoamickotlin.model.Vote

fun Idea.printIfExist() {
    if(isExist) {
        println(this)
    }
}

fun Transaction.printIfExist() {
    if(isExist) {
        println(this)
    }
}

fun Vote.printIfExist() {
    if(isExist) {
        println(this)
    }
}

fun Note.printIfExist() {
    if(isExist) {
        println(this)
    }
}
