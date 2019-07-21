package io.usoamic.cli.util

import io.usoamic.cli.exception.ValidateUtilException
import io.usoamic.usoamickotlin.exception.InvalidMnemonicPhraseException
import org.web3j.crypto.WalletUtils
import java.math.BigInteger

class ValidateUtil {
    companion object {
        fun validatePassword(password: String) = apply {
            validateThatNotEmpty(password, "Password Required")
        }

        fun validatePrivateKey(privateKey: String) = apply {
            validateThatNotEmpty(privateKey, "Private Key Required")
        }

        fun validateMnemonicPhrase(mnemonicPhrase: String) = apply {
            validateThatNotEmpty(mnemonicPhrase, "Mnemonic Phrase Required")
        }

        fun validateAddress(address: String) = apply {
            validateThatNotEmpty(address, "Address Required")
            if(!WalletUtils.isValidAddress(address)) {
                throw ValidateUtilException("Invalid Address")
            }
        }

        fun validateTransferValue(value: String) = apply {
            val intValue = value.toBigIntegerOrNull() ?: throw ValidateUtilException("Value Required")
            if(intValue > BigInteger.ZERO) {
                throw ValidateUtilException("Invalid Value")
            }
        }

        fun validateId(id: String) = apply {
            val intId = id.toBigIntegerOrNull() ?: throw ValidateUtilException("Invalid Id")
            if(intId < BigInteger.ZERO) {
                throw ValidateUtilException("Id must be greater than or equal to zero")
            }
        }

        fun validateIds(vararg ids: String) = apply {
            for(id in ids) {
                validateId(id)
            }
        }

        private fun validateThatNotEmpty(str: String, message: String) = apply {
            if(str.isEmpty()) {
                throw ValidateUtilException(message)
            }
        }
    }
}