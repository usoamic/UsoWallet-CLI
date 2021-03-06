package io.usoamic.cli.core

import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.util.MnemonicUtils
import org.web3j.crypto.Credentials
import org.web3j.crypto.Keys
import org.web3j.crypto.WalletUtils
import javax.inject.Inject

class AccountManager @Inject constructor(private val usoamic: Usoamic) {
    fun importMnemonicPhrase(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val mnemonicPhrase = args.getOrEmpty(2)

        ValidateUtil.validateMnemonicPhrase(mnemonicPhrase)
            .validatePassword(password)
        return usoamic.importMnemonic(password, mnemonicPhrase)
    }

    fun importPrivateKey(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val privateKey = args.getOrEmpty(2)

        ValidateUtil.validatePrivateKey(privateKey)
            .validatePassword(password)

        return usoamic.importPrivateKey(password, privateKey)
    }

    fun createPrivateKey(): String {
        var privateKey: String
        do {
            privateKey = Credentials.create(Keys.createEcKeyPair()).ecKeyPair.privateKey.toString(16)
        }
        while (!WalletUtils.isValidPrivateKey(privateKey))
        return privateKey
    }

    fun createMnemonicPhrase(): String {
        return MnemonicUtils.generateMnemonic()
    }
}