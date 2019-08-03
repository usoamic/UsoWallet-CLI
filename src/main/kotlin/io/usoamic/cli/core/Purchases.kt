package io.usoamic.cli.core

import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.model.Purchase
import java.math.BigInteger
import javax.inject.Inject

class Purchases @Inject constructor(private val usoamic: Usoamic) {
    fun makePurchase(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val appId = args.getOrEmpty(2)
        val purchaseId = args.getOrEmpty(3)
        val cost = args.getOrEmpty(4)
        ValidateUtil.validatePassword(password)
            .validateAppId(appId)
            .validatePurchaseId(purchaseId)
            .validateTransferValue(cost)

        return usoamic.makePurchase(password, appId, purchaseId, cost.toBigInteger())
    }

    fun getPurchaseByAddress(args: List<String>): Purchase {
        val address = args.getOrEmpty(1)
        val id = args.getOrEmpty(2)
        ValidateUtil.validateAddress(address)
            .validateId(id)

        return usoamic.getPurchaseByAddress(address, id.toBigInteger())
    }

    fun getNumberOfPurchasesByAddress(args: List<String>): BigInteger {
        val address = args.getOrEmpty(1)
        ValidateUtil.validateAddress(address)

        usoamic.getNumberOfPurchasesByAddress(address)?.let {
            return it
        }
        throw ContractNullPointerException()
    }
}