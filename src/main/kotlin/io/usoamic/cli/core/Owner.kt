package io.usoamic.cli.core

import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.usoamickotlin.core.Usoamic
import javax.inject.Inject

class Owner {
    @Inject
    lateinit var usoamic: Usoamic

    fun setFrozen(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val frozen = args.getOrEmpty(2)
        ValidateUtil.validatePassword(password)
            .validateFrozen(frozen)
        return usoamic.setFrozen(password, frozen.toBoolean())
    }

    fun setOwner(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val owner = args.getOrEmpty(2)
        ValidateUtil.validatePassword(password)
            .validateAddress(owner)
        return usoamic.setOwner(password, owner)
    }
}