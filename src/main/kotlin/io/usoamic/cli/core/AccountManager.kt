package io.usoamic.cli.core

import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.usoamickotlin.core.Usoamic
import javax.inject.Inject

class AccountManager {
    @Inject
    lateinit var usoamic: Usoamic

    fun importMnemonicPhrase(args: List<String>): String {
        val mnemonicPhrase = args.getOrEmpty(1)
        val password = args.getOrEmpty(2)

        ValidateUtil.validateMnemonicPhrase(mnemonicPhrase)
            .validatePassword(password)

        return usoamic.importMnemonic(password, mnemonicPhrase)
    }

    fun importPrivateKey(args: List<String>): String {
        val privateKey = args.getOrEmpty(1)
        val password = args.getOrEmpty(2)

        ValidateUtil.validatePrivateKey(privateKey)
            .validatePassword(password)

        return usoamic.importMnemonic(password, privateKey)
    }
}