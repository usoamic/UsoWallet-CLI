package io.usoamic.testcli.other

import io.usoamic.usoamickotlin.other.Config

class TestConfig {
    companion object {
        const val NODE: String = "https://rinkeby.infura.io:443"
        const val ACCOUNT_FILENAME: String = "test_account.json"
        const val CONTRACT_ADDRESS: String = Config.CONTRACT_ADDRESS
        const val DEFAULT_ADDRESS: String = "0xb894d4329329b6150258c0048f6dfb41257359f5"
        const val OWNER_ADDRESS: String = "0x5d8766ac0075bdf81b48f0bfcf92449e9def0f37"
        const val PASSWORD: String = "1234!"
        const val VERSION: String = "v2"
    }
}