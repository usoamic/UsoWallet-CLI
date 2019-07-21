package io.usoamic.cli

import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.getOrZero
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.exception.InvalidMnemonicPhraseException
import io.usoamic.usoamickotlin.exception.InvalidPrivateKeyException
import io.usoamic.usoamickotlin.util.Coin
import org.web3j.utils.Convert
import java.math.BigInteger
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
                    val weiBalance = usoamic.getEthBalance()
                    val ethBalance = Convert.fromWei(weiBalance.toBigDecimal(), Convert.Unit.ETHER)
                    println(ethBalance.toPlainString())
                }
                "get_uso_balance" -> {
                    val satBalance = usoamic.getUsoBalance()
                    val coinBalance = Coin.fromSat(satBalance!!).toBigDecimal()
                    println(coinBalance.toPlainString())
                }
                "eth_transfer" -> {
                    val password = args.getOrEmpty(1)
                    val to = args.getOrEmpty(2)
                    val value = args.getOrZero(3)
                    ValidateUtil.validatePassword(password)
                    ValidateUtil.validateAddress(to)
                    ValidateUtil.validateTransferValue(value)
                    val txHash = usoamic.transferEther(password, to, value.toBigInteger())
                    println(txHash)
                }
                "uso_transfer" -> {
                    val password = args.getOrEmpty(1)
                    val to = args.getOrEmpty(2)
                    val value = args.getOrZero(3)
                    ValidateUtil.validatePassword(password)
                    ValidateUtil.validateAddress(to)
                    ValidateUtil.validateTransferValue(value)
                    val txHash = usoamic.transfer(password, to, value.toBigInteger())
                    println(txHash)
                }
                "burn" -> {
                    val password = args.getOrEmpty(1)
                    val value = args.getOrZero(2)
                    ValidateUtil.validatePassword(password)
                    ValidateUtil.validateTransferValue(value)
                    val txHash = usoamic.burn(password, value.toBigInteger())
                    println(txHash)
                }
                "get_transaction" -> {
                    val txId = args.getOrEmpty(1)
                    ValidateUtil.validateTxId(txId)
                    val transaction = usoamic.getTransaction(BigInteger.ONE)
                    println(transaction)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
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