package io.usoamic.testcli

import io.usoamic.cli.Core
import io.usoamic.cli.util.Common
import io.usoamic.testcli.other.TestConfig
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.util.Coin
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.web3j.exceptions.MessageDecodingException
import java.math.BigDecimal
import java.math.BigInteger
import javax.inject.Inject

class SwapTest {
    @Inject lateinit var core: Core
    @Inject lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun withdrawEthTest() {
        assertThrows<MessageDecodingException> {
            val value = Common.convertEthToWei("0.02")
            core.getResponse("withdraw_eth ${TestConfig.PASSWORD} $value")
        }
    }

    @Test
    fun burnSwapTest() {
        val value = BigDecimal(500)
        val usoBalance = core.getResponse("uso_balance_of ${TestConfig.DEFAULT_ADDRESS}").toBigDecimal()
        val ethBalance = core.getResponse("eth_balance_of ${TestConfig.DEFAULT_ADDRESS}").toBigDecimal()

        val txHash = core.getResponse("burn_swap ${TestConfig.PASSWORD} $value")

        usoamic.waitTransactionReceipt(txHash) {
            assert(it.status != "0x0")
            val newUsoBalance = core.getResponse("uso_balance_of ${TestConfig.DEFAULT_ADDRESS}").toBigDecimal()
            val newEthBalance = core.getResponse("eth_balance_of ${TestConfig.DEFAULT_ADDRESS}").toBigDecimal()
            assert(usoBalance > newUsoBalance)
            assert(ethBalance < newEthBalance)
        }
    }

    @Test
    fun setSwapRateTest() {
        assertThrows<MessageDecodingException> {
            val swapRate = BigInteger.ONE
            core.getResponse("set_swap_rate ${TestConfig.PASSWORD} $swapRate")
        }
    }

    @Test
    fun setSwappableTest() {
        assertThrows<MessageDecodingException> {
            val swappable = true
            core.getResponse("set_swappable ${TestConfig.PASSWORD} $swappable")
        }
    }

    @Test
    fun getSwapBalanceTest() {
        val result = core.getResponse("get_swap_balance")
        assert(result.toBigDecimalOrNull() != null)
    }

    @Test
    fun getSwapRateTest() {
        val result = core.getResponse("get_swap_rate")
        val value = result.toBigDecimalOrNull()
        assert(value != null && value > BigDecimal.ZERO)
    }

    @Test
    fun getSwappableTest() {
        val result = core.getResponse("get_swappable")
        assert(result == "true" || result == "false")
    }

}