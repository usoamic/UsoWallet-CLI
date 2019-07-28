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

        fun validateDescription(description: String) = apply {
            validateThatNotEmpty(description, "Description Required")
        }

        fun validateNoteContent(content: String) = apply {
            validateThatNotEmpty(content, "Note Content Required")
        }

        fun validateComment(comment: String) = apply {
            validateThatNotEmpty(comment, "Comment Required")
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
            if(intValue <= BigInteger.ZERO) {
                throw ValidateUtilException("Invalid Value")
            }
        }

        fun validateId(id: String) = apply {
            val intId = id.toBigIntegerOrNull() ?: throw ValidateUtilException("Invalid Id")
            if(intId < BigInteger.ZERO) {
                throw ValidateUtilException("Id must be greater than or equal to zero")
            }
        }

        fun validateSwappable(swappable: String) = apply {
            validateBool(swappable, "Swappable")
        }

        fun validateFrozen(frozen: String) = apply {
            validateBool(frozen, "Frozen")
        }

        fun validateAppId(appId: String) = apply {
            validateThatNotEmpty(appId, "AppId Required")
        }

        fun validatePurchaseId(purchaseId: String) = apply {
            validateThatNotEmpty(purchaseId, "PurchaseId Required")
        }

        fun validateIds(vararg ids: String) = apply {
            for(id in ids) {
                validateId(id)
            }
        }

        private fun validateBool(bool: String, key: String) {
            if(bool.isEmpty()) {
                throw ValidateUtilException("$key Required")
            }
            if(bool != "true" && bool != "false") {
                throw ValidateUtilException("$key must be bool")
            }
        }

        private fun validateThatNotEmpty(str: String, message: String) {
            if(str.isEmpty()) {
                throw ValidateUtilException(message)
            }
        }
    }
}