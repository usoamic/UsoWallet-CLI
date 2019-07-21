package io.usoamic.cli.util

import io.usoamic.cli.exception.ValidateUtilException

class ValidateUtil {
    companion object {
        fun validatePassword(password: String) {
            validateThatNotEmpty(password, "Password Required")
        }

        fun validatePrivateKey(password: String) {
            validateThatNotEmpty(password, "Private Key Required")
        }

        private fun validateThatNotEmpty(str: String, message: String) {
            if(str.isEmpty()) {
                throw ValidateUtilException(message)
            }
        }
    }
}