package io.usoamic.testcli.other

import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.other.Contract

class TestConfig {
    companion object {
        const val ACCOUNT_FILENAME: String = "test_account.json"
        val CONTRACT_ADDRESS: String = Contract.forNetwork(NetworkType.TESTNET)
        const val DEFAULT_ADDRESS: String = "0x8b27fa2987630a1acd8d868ba84b2928de737bc2"
        const val PASSWORD: String = "1234!"
        const val VERSION: String = "v2.1.1"
        const val INFURA_PROJECT_ID: String = "d0b30b18d6334906bcbf7d30e3dfa6bb"
    }
}