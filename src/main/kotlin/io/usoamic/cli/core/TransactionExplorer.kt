package io.usoamic.cli.core

import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.model.Transaction
import java.math.BigInteger
import javax.inject.Inject

class TransactionExplorer @Inject constructor(private val usoamic: Usoamic) {
    fun getTransaction(args: List<String>): Transaction {
        val txId = args.getOrEmpty(1)
        ValidateUtil.validateId(txId)
        return usoamic.getTransaction(txId.toBigInteger())
    }

    fun getNumberOfTransactions(): BigInteger {
        usoamic.getNumberOfTransactions()?.let {
            return it
        }
        throw ContractNullPointerException()
    }

    fun getNumberOfTransactionsByAddress(args: List<String>): BigInteger {
        val address = args.getOrEmpty(1)
        ValidateUtil.validateAddress(address)
        usoamic.getNumberOfTransactionsByAddress(address)?.let {
            return it
        }
        throw ContractNullPointerException()
    }
}