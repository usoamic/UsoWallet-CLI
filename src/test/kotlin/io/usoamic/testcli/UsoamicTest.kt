package io.usoamic.testcli

import io.usoamic.cli.core.Core
import io.usoamic.testcli.other.TestConfig
import io.usoamic.testcli.util.Utils
import io.usoamic.usoamickotlin.core.Usoamic
import org.junit.jupiter.api.Test
import org.web3j.crypto.WalletUtils
import java.math.BigDecimal
import javax.inject.Inject

class UsoamicTest {
    @Inject lateinit var core: Core
    @Inject lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun getAddressTest() {
        val result = core.getResponse("get_address")
        assert(WalletUtils.isValidAddress(result))
    }

    @Test
    fun getEthBalanceTest() {
        val result = core.getResponse("get_eth_balance")
        assert(result.toBigDecimalOrNull() != null)
    }

    @Test
    fun getUsoBalanceTest() {
        val result = core.getResponse("get_uso_balance")
        assert(result.toBigDecimalOrNull() != null)
    }

    @Test
    fun transferEthTest() {
        val aliceBalance = core.getResponse("get_eth_balance")
        val value = BigDecimal("0.005")

        assert(aliceBalance.toBigDecimal() > value) {
            println("Need more ethers: $value")
        }

        val bobAddress = Utils.getNewAddress()

        val txHash = core.getResponse("eth_transfer ${TestConfig.PASSWORD} $bobAddress $value")
        usoamic.waitTransactionReceipt(txHash) {
            val newAliceBalance = core.getResponse("get_eth_balance")
            val newBobBalance = core.run { getResponse("eth_balance_of $bobAddress") }
            assert(newAliceBalance.toBigDecimal() < aliceBalance.toBigDecimal())
            assert(newBobBalance.toBigDecimal() == value)
        }
    }

    @Test
    fun transferUsoTest() {
        val aliceBalance = core.getResponse("get_uso_balance")
        val value = BigDecimal("123.4567")

        assert(aliceBalance.toBigDecimal() > value) {
            println("Need more tokens: $value")
        }

        val bobAddress = Utils.getNewAddress()

        val txHash = core.getResponse("uso_transfer ${TestConfig.PASSWORD} $bobAddress $value")
        usoamic.waitTransactionReceipt(txHash) {
            val newAliceBalance = core.getResponse("get_uso_balance")
            val newBobBalance = core.getResponse("uso_balance_of $bobAddress")
            assert(newAliceBalance.toBigDecimal() < aliceBalance.toBigDecimal())
            assert(newBobBalance.toBigDecimal().compareTo(value) == 0)
        }
    }

    @Test
    fun burnUsoTest() {
        val value = BigDecimal("121.126")
        val balance = core.getResponse("get_uso_balance")
        val txHash = core.getResponse("burn_uso ${TestConfig.PASSWORD} $value")
        usoamic.waitTransactionReceipt(txHash) {
            val newBalance = core.getResponse("get_uso_balance")
            assert(newBalance.toBigDecimal() < balance.toBigDecimal())
        }
    }

    @Test
    fun ethBalanceOfTest() {
        val result = core.getResponse("eth_balance_of ${TestConfig.DEFAULT_ADDRESS}")
        assert(result.toBigDecimalOrNull() != null)
    }

    @Test
    fun usoBalanceOfTest() {
        val result = core.getResponse("uso_balance_of ${TestConfig.DEFAULT_ADDRESS}")
        assert(result.toBigDecimalOrNull() != null)
    }

    @Test
    fun getSupplyTest() {
        val result = core.getResponse("get_supply")
        assert(result.toBigDecimal() >= BigDecimal.ZERO)
    }

    @Test
    fun getVersionTest() {
        val result = core.getResponse("get_version")
        assert(result == TestConfig.VERSION)
    }
}