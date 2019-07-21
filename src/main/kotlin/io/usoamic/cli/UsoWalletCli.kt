package io.usoamic.cli

import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.getOrZero
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.exception.InvalidMnemonicPhraseException
import io.usoamic.usoamickotlin.exception.InvalidPrivateKeyException
import java.util.*
import javax.inject.Inject


class UsoWalletCli {
    @Inject
    lateinit var usoamic: Usoamic

    init {
        App.component.inject(this)

        try {
            val input = Scanner(System.`in`)
            val line = input.nextLine()
            val args = line.split(" ")

            when (args.getOrEmpty(0)) {
                "import_mnemonic_phrase" -> {
                    val mnemonicPhrase = args.getOrEmpty(1)
                    val password = args.getOrEmpty(2)

                    ValidateUtil.validateMnemonicPhrase(mnemonicPhrase)
                    ValidateUtil.validatePassword(password)

                    val path = usoamic.importMnemonic(password, mnemonicPhrase.replace(',', ' '))
                    println("Path: $path")
                }
                "import_private_key" -> {
                    val privateKey = args.getOrEmpty(1)
                    val password = args.getOrEmpty(2)

                    ValidateUtil.validatePrivateKey(privateKey)
                    ValidateUtil.validatePassword(password)

                    val path = usoamic.importMnemonic(password, privateKey)
                    println("Path: $path")
                }
                "get_address" -> {
                    println(usoamic.address)
                }
                "get_eth_balance" -> {
                    println(usoamic.getEthBalance())
                }
                "get_uso_balance" -> {
                    println(usoamic.getUsoBalance())
                }
                "eth_transfer" -> {

                }
                "uso_transfer" -> {
                    val password = args.getOrEmpty(1)
                    val to = args.getOrEmpty(2)
                    val value = args.getOrZero(3)
                    ValidateUtil.validatePassword(password)
                    ValidateUtil.validateAddress(to)
                    ValidateUtil.validateTransferValue(value)
                    usoamic.transfer(password, to, value.toBigInteger())
                }
                "burn" -> {

                }
                "get_transaction" -> {

                }
            }
        } catch (e: Exception) {
            println(e.message)
            when (e) {
                is InvalidMnemonicPhraseException -> {
                    println("Invalid Mnemonic Phrase")
                }
                is InvalidPrivateKeyException -> {
                    println("Invalid Private Key")
                }
                else -> {
                    println("Error: ${e.message}")
                }
            }
        }

    }
}