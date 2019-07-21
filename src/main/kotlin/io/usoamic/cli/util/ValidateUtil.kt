package io.usoamic.cli.util

import io.usoamic.cli.exception.ValidateUtilException
import io.usoamic.usoamickotlin.exception.InvalidMnemonicPhraseException

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

        private fun validateThatNotEmpty(str: String, message: String) {
            if(str.isEmpty()) {
                throw ValidateUtilException(message)
            }
        }
    }
}