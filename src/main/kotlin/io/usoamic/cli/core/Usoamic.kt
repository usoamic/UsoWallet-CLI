package io.usoamic.cli.core

import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.getOrZero
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.util.Coin
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import javax.inject.Inject

class Usoamic @Inject constructor(private val usoamic: Usoamic) {
    fun getAddress(): String {
        return usoamic.address
    }

    fun getEthBalance(): String {
        val weiBalance = usoamic.getEthBalance()
        val ethBalance = convertWeiToEth(weiBalance)
        return ethBalance.toPlainString()
    }

    fun getUsoBalance(): String {
        val satBalance = usoamic.getUsoBalance()
        satBalance?.let {
            val coinBalance = Coin.fromSat(satBalance).toBigDecimal()
            return coinBalance.toPlainString()
        }
        throw ContractNullPointerException()
    }

    fun transferEth(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val to = args.getOrEmpty(2)
        val sValue = args.getOrZero(3)
        val value = Convert.toWei(sValue, Convert.Unit.ETHER).toBigInteger()
        ValidateUtil.validatePassword(password)
            .validateAddress(to)
            .validateTransferValue(value.toString())
        return usoamic.transferEth(password, to, value)
    }

    fun transferUso(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val to = args.getOrEmpty(2)
        val value = args.getOrZero(3)
        ValidateUtil.validatePassword(password)
            .validateAddress(to)
            .validateTransferValue(value)
        return usoamic.transferUso(password, to, value.toBigInteger())
    }

    fun burnUso(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val value = args.getOrZero(2)
        ValidateUtil.validatePassword(password)
            .validateTransferValue(value)
        return usoamic.burn(password, value.toBigInteger())
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

    private fun convertWeiToEth(wei: BigInteger): BigDecimal {
        return Convert.fromWei(wei.toBigDecimal(), Convert.Unit.ETHER)
    }
}