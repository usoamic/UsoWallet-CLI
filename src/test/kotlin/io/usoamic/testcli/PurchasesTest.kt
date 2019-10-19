package io.usoamic.testcli

import io.usoamic.cli.core.Core
import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.testcli.other.TestConfig
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.util.Coin
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import javax.inject.Inject
import kotlin.random.Random

class PurchasesTest {
    @Inject lateinit var core: Core
    @Inject lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun makePurchaseTest() {
        val appId = "appId" + Random.nextInt()
        val purchaseId = "purchaseId" + Random.nextInt()
        val cost = Coin.ONE.toSat()

        val numberOfPurchases = core.getResponse("get_number_of_purchases_by_address ${TestConfig.DEFAULT_ADDRESS}")
        val txHash = core.getResponse("make_purchase ${TestConfig.PASSWORD} $appId $purchaseId $cost")

        usoamic.waitTransactionReceipt(txHash) {
            val purchase = core.getResponse("get_purchase_by_address ${TestConfig.DEFAULT_ADDRESS} $numberOfPurchases")
            assert(purchase.contains("isExist=true"))
            assert(purchase.contains("appId=$appId"))
            assert(purchase.contains("id=$numberOfPurchases"))
            assert(purchase.contains("purchaseId=$purchaseId"))
            assert(purchase.contains("cost=$cost"))
        }
    }

    @Test
    fun getPurchaseByAddressTest() {
        val id = BigInteger.ZERO!!
        val numberOfPurchases = core.getResponse("get_number_of_purchases_by_address ${TestConfig.DEFAULT_ADDRESS}").toBigInteger()
        val isExist = numberOfPurchases > id

        if(isExist) {
            val purchase = core.getResponse("get_purchase_by_address ${TestConfig.DEFAULT_ADDRESS} $id")
            assert(purchase.contains("id=$id"))
        }

        assertThrows<ObjectNotFoundException> {
            core.getResponse("get_purchase_by_address ${TestConfig.DEFAULT_ADDRESS} $numberOfPurchases")
        }
    }

    @Test
    fun getNumberOfPurchasesByAddressTest() {
        val result = core.getResponse("get_number_of_purchases_by_address ${TestConfig.DEFAULT_ADDRESS}")
        assert(result.toBigInteger() >= BigInteger.ZERO)
    }
}