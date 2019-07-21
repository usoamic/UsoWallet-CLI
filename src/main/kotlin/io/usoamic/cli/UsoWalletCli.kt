package io.usoamic.cli

import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.getOrZero
import io.usoamic.cli.util.printIfExist
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
            println("Address: ${usoamic.address}")

            val input = Scanner(System.`in`)
            print("> ")
            val line = input.nextLine()
            val args = line.split(" ") //TODO: Change delimiters


            when (args.getOrEmpty(0)) {
                //add
                "import_mnemonic_phrase" -> {
                    val mnemonicPhrase = args.getOrEmpty(1)
                    val password = args.getOrEmpty(2)

                    ValidateUtil.validateMnemonicPhrase(mnemonicPhrase)
                                .validatePassword(password)

                    val path = usoamic.importMnemonic(password, mnemonicPhrase.replace(',', ' '))
                    println("Path: $path")
                }
                "import_private_key" -> {
                    val privateKey = args.getOrEmpty(1)
                    val password = args.getOrEmpty(2)

                    ValidateUtil.validatePrivateKey(privateKey)
                                .validatePassword(password)

                    val path = usoamic.importMnemonic(password, privateKey)
                    println("Path: $path")
                }
                //common
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
                                .validateAddress(to)
                                .validateTransferValue(value)
                    val txHash = usoamic.transferEther(password, to, value.toBigInteger())
                    println(txHash)
                }
                "uso_transfer" -> {
                    val password = args.getOrEmpty(1)
                    val to = args.getOrEmpty(2)
                    val value = args.getOrZero(3)
                    ValidateUtil.validatePassword(password)
                                .validateAddress(to)
                                .validateTransferValue(value)
                    val txHash = usoamic.transfer(password, to, value.toBigInteger())
                    println(txHash)
                }
                "burn" -> {
                    val password = args.getOrEmpty(1)
                    val value = args.getOrZero(2)
                    ValidateUtil.validatePassword(password)
                                .validateTransferValue(value)
                    val txHash = usoamic.burn(password, value.toBigInteger())
                    println(txHash)
                }
                //idea
                "add_idea" -> {
                    val password = args.getOrEmpty(1)
                    val description = args.getOrEmpty(2)
                    ValidateUtil.validatePassword(password)
                                .validateDescription(description)
                    val txHash = usoamic.addIdea(password, description)
                    println(txHash)
                }
                "get_idea" -> {
                    val ideaRefId = args.getOrEmpty(1)
                    ValidateUtil.validateId(ideaRefId)
                    val idea = usoamic.getIdea(ideaRefId.toBigInteger())
                    idea.printIfExist()
                }
                "get_idea_by_author" -> {
                    val address = args.getOrEmpty(1)
                    val ideaId = args.getOrEmpty(2)
                    ValidateUtil.validateAddress(address)
                                .validateId(ideaId)
                    val idea = usoamic.getIdeaByAddress(address, ideaId.toBigInteger())
                    idea.printIfExist()
                }
                "support_idea" -> {
                    val password = args.getOrEmpty(1)
                    val ideaRefId = args.getOrEmpty(2)
                    val comment = args.getOrEmpty(3)

                    ValidateUtil.validatePassword(password)
                        .validateId(ideaRefId)
                        .validateComment(comment)

                    val txHash = usoamic.supportIdea(password, ideaRefId.toBigInteger(), comment)
                    println(txHash)
                }
                "abstain_idea" -> {
                    //TODO: Fill block
                }
                "against_idea" -> {
                    //TODO: Fill block
                }
                "get_vote" -> {
                    val ideaRefId = args.getOrEmpty(1)
                    val voteRefId = args.getOrEmpty(2)
                    ValidateUtil.validateIds(ideaRefId, voteRefId)
                    val vote = usoamic.getVote(ideaRefId.toBigInteger(), voteRefId.toBigInteger())
                    vote.printIfExist()
                }

                //transactions
                "get_transaction" -> {
                    val txId = args.getOrEmpty(1)
                    ValidateUtil.validateId(txId)
                    val transaction = usoamic.getTransaction(BigInteger.ONE)
                    transaction.printIfExist()
                }
            }
        } catch (e: Exception) {
            when (e) {
                is InvalidMnemonicPhraseException -> {
                    println("Invalid Mnemonic Phrase")
                }
                is InvalidPrivateKeyException -> {
                    println("Invalid Private Key")
                }
                is ObjectNotFoundException -> {
                    println("Not found")
                }
                else -> {
                    println("Error: ${e.message}")
                    e.printStackTrace()
                }
            }
        }

    }
}