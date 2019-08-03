package io.usoamic.testcli

import io.usoamic.cli.core.Core
import io.usoamic.testcli.other.TestConfig
import io.usoamic.testcli.util.Utils
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.util.Coin
import org.junit.jupiter.api.Test
import java.math.BigInteger
import javax.inject.Inject

class TransactionExplorerTest {
    @Inject lateinit var core: Core
    @Inject lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun getTransactionTest() {
        val address = Utils.getNewAddress()
        val coin = Coin.fromCoin("10.341")
        val lastTxNumber = core.getResponse("get_number_of_transactions")
        val txHash = core.getResponse("uso_transfer ${TestConfig.PASSWORD} $address ${coin.toBigDecimal()}")
        usoamic.waitTransactionReceipt(txHash) {
            val result = core.getResponse("get_transaction $lastTxNumber")
            assert(result.contains("isExist=true"))
            assert(result.contains("txId=$lastTxNumber"))
            assert(result.contains("from=${TestConfig.DEFAULT_ADDRESS}"))
            assert(result.contains("to=$address"))
            assert(result.contains("value=${coin.toBigDecimal()}"))
        }
    }

    @Test
    fun getNumberOfTransactionsTest() {
        val result = core.getResponse("get_number_of_transactions")
        assert(result.toBigInteger() >= BigInteger.ZERO)
    }

    @Test
    fun getNumberOfTransactionsByAddressTest() {
        val address = Utils.getNewAddress()
        val numberOfTx = core.getResponse("get_number_of_transactions_by_address $address")
        assert(numberOfTx.toBigInteger() == BigInteger.ZERO)

        val defaultNumberOfTx = core.getResponse("get_number_of_transactions_by_address ${TestConfig.DEFAULT_ADDRESS}")
        assert(defaultNumberOfTx.toBigInteger() > BigInteger.ZERO)
    }
}