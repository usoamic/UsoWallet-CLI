package io.usoamic.cli.core

import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.util.Common
import io.usoamic.cli.util.Common.Companion.convertEthToWei
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.getOrZero
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.util.Coin
import java.math.BigInteger
import javax.inject.Inject
import io.usoamic.cli.util.Common.Companion.convertWeiToEth
import java.math.BigDecimal

class Swap @Inject constructor(private val usoamic: Usoamic) {
    fun withdrawEth(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val value = args.getOrEmpty(2)

        ValidateUtil.validatePassword(password)
                    .validateTransferValue(value)

        val bdValue = convertEthToWei(value)

        return usoamic.withdrawEth(password, bdValue)
    }

    fun burnSwap(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val value = args.getOrZero(2)

        ValidateUtil.validatePassword(password)
                    .validateTransferValue(value)

        val bdValue = Coin.fromCoin(value).toSat()
        return usoamic.burnSwap(password, bdValue)
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

    fun getSwapBalance(): BigDecimal {
        usoamic.getSwapBalance()?.let {
            return convertWeiToEth(it)
        }
        throw ContractNullPointerException()
    }

    fun getSwapRate(): BigDecimal {
        usoamic.getSwapRate()?.let {
            return convertWeiToEth(it)
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