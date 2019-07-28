package io.usoamic.cli

import io.usoamic.cli.core.*
import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.getOrZero
import io.usoamic.cli.util.printIfExist
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
    lateinit var notes: Notes

    @Inject
    lateinit var owner: Owner

    @Inject
    lateinit var purchases: Purchases

    @Inject
    lateinit var swap: Swap

    @Inject
    lateinit var transactionExplorer: TransactionExplorer

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
                        println(notes.addPublicNote(args))
                    }
                    "add_unlisted_note" -> {
                        println(notes.addUnlistedNote(args))
                    }
                    "get_number_of_public_notes" -> {
                        println(notes.getNumberOfPublicNotes())
                    }
                    "get_number_of_notes_by_author" -> {
                        println(notes.getNumberOfNotesByAuthor(args))
                    }
                    "get_note_by_author" -> {
                        notes.getNoteByAuthor(args).printIfExist()
                    }
                    "get_note" -> {
                        notes.getNote(args).printIfExist()
                    }
                    //Owner
                    "set_frozen" -> {
                        println(owner.setFrozen(args))
                    }
                    "set_owner" -> {
                        println(owner.setOwner(args))
                    }
                    //Purchases
                    "make_purchase" -> {
                        println(purchases.makePurchase(args))
                    }
                    "get_purchase_by_address" -> {
                        purchases.getPurchaseByAddress(args).printIfExist()
                    }
                    "get_number_of_purchase_by_address" -> {
                        println(purchases.getNumberOfPurchasesByAddress(args))
                    }
                    //Swap
                    "withdraw_eth" -> {
                        println(swap.withdrawEth(args))
                    }
                    "burn_swap" -> {
                        println(swap.burnSwap(args))
                    }
                    "set_swap_rate" -> {
                        println(swap.setSwapRate(args))
                    }
                    "set_swappable" -> {
                        println(swap.setSwappable(args))
                    }
                    "get_swap_balance" -> {
                        println(swap.usoamic)
                    }
                    "get_swap_rate" -> {
                        println(swap.getSwapRate())
                    }
                    "get_swappable" -> {
                        println(swap.getSwappable())
                    }
                    //transactions
                    "get_transaction" -> {
                        transactionExplorer.getTransaction(args).printIfExist()
                    }
                    "get_number_of_transactions" -> {
                        println(transactionExplorer.getNumberOfTransactions())
                    }
                    "get_number_of_transactions_by_address" -> {
                        println(transactionExplorer.getNumberOfTransactionsByAddress(args))
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