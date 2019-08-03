package io.usoamic.cli.util

import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

class Common {
    companion object {
        fun convertEthToWei(wei: BigInteger): BigInteger {
            return Convert.toWei(wei.toBigDecimal(), Convert.Unit.ETHER).toBigInteger()
        }

        fun convertEthToWei(wei: String): BigInteger {
            return Convert.toWei(wei.toBigDecimal(), Convert.Unit.ETHER).toBigInteger()
        }

        fun convertWeiToEth(wei: BigInteger): BigDecimal {
            return Convert.fromWei(wei.toBigDecimal(), Convert.Unit.ETHER)
        }

        fun convertWeiToEth(wei: String): BigDecimal {
            return Convert.fromWei(wei.toBigDecimal(), Convert.Unit.ETHER)
        }
    }
}