package io.usoamic.cli.core

import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.getOrZero
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.util.Coin
import org.web3j.utils.Convert
import java.lang.NullPointerException
import java.math.BigInteger
import javax.inject.Inject

class Usoamic {
    @Inject
    lateinit var usoamic: Usoamic

    fun getAddress(): String {
        return usoamic.address
    }

    fun getEthBalance(): String {
        val weiBalance = usoamic.getEthBalance()
        val ethBalance = Convert.fromWei(weiBalance.toBigDecimal(), Convert.Unit.ETHER)
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
        val value = args.getOrZero(3)
        ValidateUtil.validatePassword(password)
            .validateAddress(to)
            .validateTransferValue(value)
        return usoamic.transferEth(password, to, value.toBigInteger())
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

    fun balanceOf(args: List<String>): BigInteger {
        val address = args.getOrEmpty(1)
        ValidateUtil.validateAddress(address)
        usoamic.balanceOf(address)?.let {
            return it
        }
        throw ContractNullPointerException()
    }

    fun getSupply(): BigInteger {
        usoamic.getSupply()?.let {
            return it
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