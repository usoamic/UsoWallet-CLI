package io.usoamic.cli.core

import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.util.Common.Companion.convertEthToWei
import io.usoamic.cli.util.Common.Companion.convertWeiToEth
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.getOrZero
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.util.Coin
import java.math.BigDecimal
import javax.inject.Inject

class Usoamic @Inject constructor(private val usoamic: Usoamic) {
    fun getAddress(): String {
        return usoamic.address
    }

    fun getEthBalance(): String {
        val weiBalance = usoamic.getEthBalance()
        return convertWeiToEth(weiBalance).toPlainString()
    }

    fun getUsoBalance(): String {
        val satBalance = usoamic.getUsoBalance()
        satBalance?.let {
            return Coin.fromSat(satBalance).toBigDecimal().toPlainString()
        }
        throw ContractNullPointerException()
    }

    fun transferEth(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val to = args.getOrEmpty(2)
        val value = args.getOrZero(3)

        ValidateUtil.validatePassword(password)
                    .validateAddress(to)
                    .validateTransferValue(value)

        val biValue = convertEthToWei(value)

        return usoamic.transferEth(password, to, biValue)
    }

    fun transferUso(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val to = args.getOrEmpty(2)
        val value = args.getOrZero(3)

        ValidateUtil.validatePassword(password)
                    .validateAddress(to)
                    .validateTransferValue(value)

        val biValue = Coin.fromCoin(value).toSat()

        return usoamic.transferUso(password, to, biValue)
    }

    fun burnUso(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val value = args.getOrZero(2)

        ValidateUtil.validatePassword(password)
                    .validateTransferValue(value)

        val biValue = Coin.fromCoin(value).toSat()

        return usoamic.burn(password, biValue)
    }

    fun usoBalanceOf(args: List<String>): BigDecimal {
        val address = args.getOrEmpty(1)
        ValidateUtil.validateAddress(address)
        usoamic.balanceOf(address)?.let {
            return Coin.fromSat(it).toBigDecimal()
        }
        throw ContractNullPointerException()
    }

    fun ethBalanceOf(args: List<String>): BigDecimal {
        val address = args.getOrEmpty(1)
        ValidateUtil.validateAddress(address)
        val weiBalance = usoamic.getEthBalance(address)
        return convertWeiToEth(weiBalance)
    }

    fun getSupply(): BigDecimal {
        usoamic.getSupply()?.let {
            return Coin.fromSat(it).toBigDecimal()
        }
        throw ContractNullPointerException()
    }

    fun getVersion(): String {
        usoamic.getVersion()?.let {
            return it
        }
        throw ContractNullPointerException()
    }
}