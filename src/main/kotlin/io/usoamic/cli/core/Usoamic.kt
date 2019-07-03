package io.usoamic.cli.core

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger
import org.web3j.abi.datatypes.Function
import java.lang.Exception


class Usoamic constructor(filename: String) : TransactionManager(filename) {
    init {

    }

    @Throws(Exception::class)
    fun balanceOf(address: String): BigInteger? {
        val function = Function(
            "balanceOf",
            listOf(Address(address)),
            listOf(object: TypeReference<Uint256>() { })
        )
        val result = executeCallSingleValueReturn(function)
        return if(result == null) result else result as BigInteger
    }
}