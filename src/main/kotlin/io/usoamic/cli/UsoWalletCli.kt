package io.usoamic.cli

import io.usoamic.cli.core.AccountManager
import io.usoamic.cli.core.Ideas
import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.getOrZero
import io.usoamic.cli.util.printIfExist
import io.usoamic.cli.core.Usoamic
import io.usoamic.usoamickotlin.exception.InvalidMnemonicPhraseException
import io.usoamic.usoamickotlin.exception.InvalidPrivateKeyException
import io.usoamic.usoamickotlin.util.Coin
import org.web3j.utils.Convert
import java.math.BigInteger
import java.util.*
import javax.inject.Inject


class UsoWalletCli {
    @Inject
    lateinit var accountManager: AccountManager
    @Inject
    lateinit var ideas: Ideas
    @Inject
    lateinit var usoamic: Usoamic


    init {
        App.component.inject(this)


        println("Address: ${usoamic.getAddress()}")

        val input = Scanner(System.`in`)
        print("> ")
        while (input.hasNextLine()) {
            try {
                val line = input.nextLine()
                val args = line.split(Regex(" (?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*\$)"))
                when (args.getOrEmpty(0)) {
                    //add
                    "import_mnemonic_phrase" -> {
                        println(accountManager.importMnemonicPhrase(args))
                    }
                    "import_private_key" -> {
                        println(accountManager.importPrivateKey(args))
                    }
                    //common
                    "get_address" -> {
                        println(usoamic.getAddress())
                    }
                    "get_eth_balance" -> {
                        println(usoamic.getEthBalance())
                    }
                    "get_uso_balance" -> {
                        println(usoamic.getUsoBalance())
                    }
                    "eth_transfer" -> {
                        println(usoamic.transferEth(args))
                    }
                    "uso_transfer" -> {
                        println(usoamic.transferUso(args))
                    }
                    "burn" -> {
                        println(usoamic.burnUso(args))
                    }
                    "balance_of" -> {
                        println(usoamic.balanceOf(args))
                    }
                    "get_supply" -> {
                        println(usoamic.getSupply())
                    }
                    "get_contract_version" -> {
                        println(usoamic.getVersion())
                    }
                    //idea
                    "add_idea" -> {
                        println(ideas.addIdea(args))
                    }
                    "get_idea" -> {
                        ideas.getIdea(args).printIfExist()
                    }
                    "get_idea_by_author" -> {
                        ideas.getIdeasByAuthor(args).printIfExist()
                    }
                    //Vote for idea
                    "support_idea" -> {
                        println(ideas.supportIdea(args))
                    }
                    "abstain_idea" -> {
                        println(ideas.abstainIdea(args))
                    }
                    "against_idea" -> {
                        println(ideas.againstIdea(args))
                    }
                    "get_vote" -> {
                        ideas.getVote(args).printIfExist()
                    }
                    "get_vote_by_voter" -> {
                        ideas.getVoteByVoter(args).printIfExist()
                    }
                    "get_number_of_ideas_by_author" -> {
                        println(ideas.getNumberOfIdeasByAuthor(args))
                    }
                    "get_number_of_ideas" -> {
                        println(ideas.getNumberOfIdeas())
                    }
                    "get_number_of_votes_by_voter" -> {
                        println(ideas.getNumberOfVotesByVoter(args))
                    }
                    //Notes
                    "add_public_note" -> {
                        val password = args.getOrEmpty(1)
                        val content = args.getOrEmpty(2)
                        ValidateUtil.validatePassword(password)
                            .validateDescription(content)
                        val txHash = usoamic.addIdea(password, content)
                        println(txHash)
                    }
                    "add_unlisted_note" -> {
                        //TODO: Fill code block
                    }
                    "get_number_of_public_notes" -> {
                        val numberOfNotes = usoamic.getNumberOfPublicNotes()
                        println(numberOfNotes)
                    }
                    "get_number_of_notes_by_author" -> {
                        val author = args.getOrEmpty(1)
                        ValidateUtil.validateAddress(author)
                        val numberOfNotes = usoamic.getNumberOfNotesByAuthor(author)
                        println(numberOfNotes)
                    }
                    "get_note_by_author" -> {
                        val author = args.getOrEmpty(1)
                        val noteId = args.getOrEmpty(2)
                        ValidateUtil.validateAddress(author)
                            .validateId(noteId)
                        val note = usoamic.getNoteByAuthor(author, noteId.toBigInteger())
                        note.printIfExist()
                    }
                    "get_note" -> {
                        val noteRefId = args.getOrEmpty(2)
                        ValidateUtil.validateId(noteRefId)
                        val note = usoamic.getNote(noteRefId.toBigInteger())
                        note.printIfExist()
                    }
                    //Owner
                    "set_frozen" -> {
                        val password = args.getOrEmpty(1)
                        val frozen = args.getOrEmpty(2)
                        ValidateUtil.validatePassword(password)
                            .validateFrozen(frozen)
                        val txHash = usoamic.setFrozen(password, frozen.toBoolean())
                        println(txHash)
                    }
                    "set_owner" -> {
                        val password = args.getOrEmpty(1)
                        val owner = args.getOrEmpty(2)
                        ValidateUtil.validatePassword(password)
                            .validateAddress(owner)
                        val txHash = usoamic.setOwner(password, owner)
                        println(txHash)
                    }
                    //Purchases
                    "make_purchase" -> {
                        val password = args.getOrEmpty(1)
                        val appId = args.getOrEmpty(2)
                        val purchaseId = args.getOrEmpty(3)
                        val cost = args.getOrEmpty(4)
                        ValidateUtil.validatePassword(password)
                            .validateAppId(appId)
                            .validatePurchaseId(purchaseId)
                            .validateTransferValue(cost)

                        val txHash = usoamic.makePurchase(password, appId, purchaseId, cost.toBigInteger())
                        println(txHash)
                    }
                    "get_purchase_by_address" -> {
                        val address = args.getOrEmpty(1)
                        val id = args.getOrEmpty(2)
                        ValidateUtil.validateAddress(address)
                            .validateId(id)

                        val purchase = usoamic.getPurchaseByAddress(address, id.toBigInteger())
                        println(purchase)
                    }
                    "get_number_of_purchase_by_address" -> {
                        val address = args.getOrEmpty(1)
                        ValidateUtil.validateAddress(address)

                        val numberOfPurchases = usoamic.getNumberOfPurchasesByAddress(address)
                        println(numberOfPurchases)
                    }
                    //Swap
                    "withdraw_eth" -> {
                        val password = args.getOrEmpty(1)
                        val value = args.getOrEmpty(2)
                        ValidateUtil.validatePassword(password)
                            .validateTransferValue(value)
                        val txHash = usoamic.withdrawEth(password, value.toBigInteger())
                        println(txHash)
                    }
                    "burn_swap" -> {
                        val password = args.getOrEmpty(1)
                        val value = args.getOrZero(2)
                        ValidateUtil.validatePassword(password)
                            .validateTransferValue(value)
                        val txHash = usoamic.burnSwap(password, value.toBigInteger())
                        println(txHash)
                    }
                    "set_swap_rate" -> {
                        val password = args.getOrEmpty(1)
                        val swapRate = args.getOrZero(2)
                        ValidateUtil.validatePassword(password)
                            .validateTransferValue(swapRate)
                        val txHash = usoamic.setSwapRate(password, swapRate.toBigInteger())
                        println(txHash)
                    }
                    "set_swappable" -> {
                        val password = args.getOrEmpty(1)
                        val swappable = args.getOrEmpty(2)
                        ValidateUtil.validatePassword(password)
                            .validateSwappable(swappable)
                        val txHash = usoamic.setFrozen(password, swappable.toBoolean())
                        println(txHash)
                    }
                    "get_swap_balance" -> {
                        val swapBalance = usoamic.getSwapBalance()
                        println(swapBalance)
                    }
                    "get_swap_rate" -> {
                        val swapRate = usoamic.getSwapRate()
                        println(swapRate)
                    }
                    "get_swappable" -> {
                        val swappable = usoamic.getSwappable()
                        println(swappable)
                    }
                    //transactions
                    "get_transaction" -> {
                        val txId = args.getOrEmpty(1)
                        ValidateUtil.validateId(txId)
                        val transaction = usoamic.getTransaction(BigInteger.ONE)
                        transaction.printIfExist()
                    }
                    "get_number_of_transactions" -> {
                        val numberOfTransactions = usoamic.getNumberOfTransactions()
                        println(numberOfTransactions)
                    }
                    "get_number_of_transactions_by_address" -> {
                        val address = args.getOrEmpty(1)
                        ValidateUtil.validateAddress(address)
                        val numberOfTransactions = usoamic.getNumberOfTransactionsByAddress(address)
                        println(numberOfTransactions)
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
            print("> ")
        }
    }
}