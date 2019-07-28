package io.usoamic.testcli

import io.usoamic.cli.Core
import io.usoamic.testcli.other.TestConfig
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.util.Coin
import org.junit.jupiter.api.Test
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
        val address = core.getResponse("get_address")

        val appId = "appId" + Random.nextInt()
        val purchaseId = "purchaseId" + Random.nextInt()
        val cost = Coin.ONE.toSat()

        val numberOfPurchases = core.getResponse("get_number_of_purchases_by_address $address")
        val txHash = core.getResponse("make_purchase ${TestConfig.PASSWORD} $appId $purchaseId $cost")

        usoamic.waitTransactionReceipt(txHash) {
            val purchase = core.getResponse("get_purchase_by_address $address $numberOfPurchases")
            assert(purchase.contains("isExist=true"))
            assert(purchase.contains("appId=$appId"))
            assert(purchase.contains("id=$numberOfPurchases"))
            assert(purchase.contains("purchaseId=$purchaseId"))
            assert(purchase.contains("cost=$cost"))
        }
    }
}