package io.usoamic.cli.core

import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.getOrZero
import io.usoamic.usoamickotlin.core.Usoamic
import java.math.BigInteger
import javax.inject.Inject

class Swap @Inject constructor(private val usoamic: Usoamic) {
    fun withdrawEth(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val value = args.getOrEmpty(2)
        ValidateUtil.validatePassword(password)
            .validateTransferValue(value)
        return usoamic.withdrawEth(password, value.toBigInteger())
    }

    fun burnSwap(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val value = args.getOrZero(2)
        ValidateUtil.validatePassword(password)
            .validateTransferValue(value)
        return usoamic.burnSwap(password, value.toBigInteger())
    }

    fun setSwapRate(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val swapRate = args.getOrZero(2)
        ValidateUtil.validatePassword(password)
            .validateTransferValue(swapRate)
        return usoamic.setSwapRate(password, swapRate.toBigInteger())
    }

    fun setSwappable(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val swappable = args.getOrEmpty(2)
        ValidateUtil.validatePassword(password)
            .validateSwappable(swappable)
        return usoamic.setSwappable(password, swappable.toBoolean())
    }

    fun getSwapBalance(): BigInteger {
        usoamic.getSwapBalance()?.let {
            return it
        }
        throw ContractNullPointerException()
    }

    fun getSwapRate(): BigInteger {
        usoamic.getSwapRate()?.let {
            return it
        }
        throw ContractNullPointerException()
    }

    fun getSwappable(): Boolean {
        usoamic.getSwappable()?.let {
            return it
        }
        throw ContractNullPointerException()
    }
}