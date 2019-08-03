package io.usoamic.testcli

import io.usoamic.cli.core.Core
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
        val swappable = usoamic.getSwappable()
        if(swappable!!) {
            val value = Coin.ONE_HUNDRED.toBigDecimal()
            val usoBalance = core.getResponse("uso_balance_of ${TestConfig.DEFAULT_ADDRESS}").toBigDecimal()
            val ethBalance = core.getResponse("eth_balance_of ${TestConfig.DEFAULT_ADDRESS}").toBigDecimal()
            val contractBalance = core.getResponse("eth_balance_of ${TestConfig.CONTRACT_ADDRESS}").toBigDecimal()
            val swapRatePerSat = core.getResponse("get_swap_rate").toBigDecimal()

            val ethNumber = swapRatePerSat.multiply(Coin.fromCoin(value).toSat().toBigDecimal())

            assert(contractBalance >= ethNumber) {
                println("Need more ethers on contract address: ${TestConfig.CONTRACT_ADDRESS}")
            }
            assert(usoBalance > value) {
                println("Need more USO")
            }

            val txHash = core.getResponse("burn_swap ${TestConfig.PASSWORD} $value")

            usoamic.waitTransactionReceipt(txHash) {
                assert(it.status != "0x0")
                val newUsoBalance = core.getResponse("uso_balance_of ${TestConfig.DEFAULT_ADDRESS}").toBigDecimal()
                val newEthBalance = core.getResponse("eth_balance_of ${TestConfig.DEFAULT_ADDRESS}").toBigDecimal()
                assert(usoBalance > newUsoBalance)
                assert(ethBalance < newEthBalance)
            }
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