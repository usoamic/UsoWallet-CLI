package io.usoamic.testcli.util

import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys

class Utils {
    companion object {
        fun getNewAddress(): String {
            return Credentials.create(Keys.createEcKeyPair()).address
        }
    }
}