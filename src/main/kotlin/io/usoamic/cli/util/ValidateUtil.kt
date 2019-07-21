package io.usoamic.cli.util

import io.usoamic.cli.exception.ValidateUtilException
import io.usoamic.usoamickotlin.exception.InvalidMnemonicPhraseException
import org.web3j.crypto.WalletUtils

class ValidateUtil {
    companion object {
        fun validatePassword(password: String) {
            validateThatNotEmpty(password, "Password Required")
        }

        fun validatePrivateKey(privateKey: String) {
            validateThatNotEmpty(privateKey, "Private Key Required")
        }

        fun validateMnemonicPhrase(mnemonicPhrase: String) {
            validateThatNotEmpty(mnemonicPhrase, "Mnemonic Phrase Required")
        }

        fun validateAddress(address: String) {
            validateThatNotEmpty(address, "Address Required")
            if(WalletUtils.isValidAddress(address)) {
                throw ValidateUtilException("Invalid Address")
            }
        }

        fun validateTransferValue(value: String) {
            value.toIntOrNull() ?: throw ValidateUtilException("Invalid value")
        }

        private fun validateThatNotEmpty(str: String, message: String) {
            if(str.isEmpty()) {
                throw ValidateUtilException(message)
            }
        }
    }
}