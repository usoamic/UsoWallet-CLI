package io.usoamic.cli.util

import io.usoamic.cli.exception.ValidateUtilException
import io.usoamic.usoamickotlin.exception.InvalidMnemonicPhraseException
import org.web3j.crypto.WalletUtils
import java.math.BigInteger

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
            if(!WalletUtils.isValidAddress(address)) {
                throw ValidateUtilException("Invalid Address")
            }
        }

        fun validateTransferValue(value: String) {
            val intValue = value.toBigIntegerOrNull() ?: throw ValidateUtilException("Value Required")
            if(intValue > BigInteger.ZERO) {
                throw ValidateUtilException("Invalid Value")
            }
        }

        fun validateId(id: String) {
            val intId = id.toBigIntegerOrNull() ?: throw ValidateUtilException("Invalid Id")
            if(intId < BigInteger.ZERO) {
                throw ValidateUtilException("Id must be greater than or equal to zero")
            }
        }

        private fun validateThatNotEmpty(str: String, message: String) {
            if(str.isEmpty()) {
                throw ValidateUtilException(message)
            }
        }
    }
}