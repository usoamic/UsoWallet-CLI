package io.usoamic.cli

import io.usoamic.cli.core.*
import io.usoamic.cli.exception.CommandNotFoundException
import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.toStringIfExist
import io.usoamic.usoamickotlin.exception.InvalidMnemonicPhraseException
import io.usoamic.usoamickotlin.exception.InvalidPrivateKeyException
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
                println(getResponse(args))
            }
            catch (e: Exception) {
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
                    is ContractNullPointerException -> {
                        println("Response is null")
                    }
                    is CommandNotFoundException -> {
                        println("Command Not Found")
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

    private fun getResponse(args: List<String>): String {
        return when (args.getOrEmpty(0)) {
            "import_mnemonic_phrase" -> accountManager.importMnemonicPhrase(args)
            "import_private_key" -> accountManager.importPrivateKey(args)
            "get_address" -> usoamic.getAddress()
            "get_eth_balance" -> usoamic.getEthBalance()
            "get_uso_balance" -> usoamic.getUsoBalance()
            "eth_transfer" -> usoamic.transferEth(args)
            "uso_transfer" -> usoamic.transferUso(args)
            "burn" -> usoamic.burnUso(args)
            "balance_of" -> usoamic.balanceOf(args).toString()
            "get_supply" -> usoamic.getSupply().toString()
            "get_contract_version" -> usoamic.getVersion()
            "add_idea" -> ideas.addIdea(args)
            "get_idea" -> ideas.getIdea(args).toStringIfExist()
            "get_idea_by_author" -> ideas.getIdeasByAuthor(args).toStringIfExist()
            "support_idea" -> ideas.supportIdea(args)
            "abstain_idea" -> ideas.abstainIdea(args)
            "against_idea" -> ideas.againstIdea(args)
            "get_vote" -> ideas.getVote(args).toStringIfExist()
            "get_vote_by_voter" -> ideas.getVoteByVoter(args).toStringIfExist()
            "get_number_of_ideas_by_author" -> ideas.getNumberOfIdeasByAuthor(args).toString()
            "get_number_of_ideas" -> ideas.getNumberOfIdeas().toString()
            "get_number_of_votes_by_voter" -> ideas.getNumberOfVotesByVoter(args).toString()
            "add_public_note" -> notes.addPublicNote(args)
            "add_unlisted_note" -> notes.addUnlistedNote(args)
            "get_number_of_public_notes" -> notes.getNumberOfPublicNotes().toString()
            "get_number_of_notes_by_author" -> notes.getNumberOfNotesByAuthor(args).toString()
            "get_note_by_author" -> notes.getNoteByAuthor(args).toStringIfExist()
            "get_note" -> notes.getNote(args).toStringIfExist()
            "set_frozen" -> owner.setFrozen(args)
            "set_owner" -> owner.setOwner(args)
            "make_purchase" -> purchases.makePurchase(args)
            "get_purchase_by_address" -> purchases.getPurchaseByAddress(args).toStringIfExist()
            "get_number_of_purchase_by_address" -> purchases.getNumberOfPurchasesByAddress(args).toString()
            "withdraw_eth" -> swap.withdrawEth(args)
            "burn_swap" -> swap.burnSwap(args)
            "set_swap_rate" -> swap.setSwapRate(args)
            "set_swappable" -> swap.setSwappable(args)
            "get_swap_balance" -> swap.getSwapBalance().toString()
            "get_swap_rate" -> swap.getSwapRate().toString()
            "get_swappable" -> swap.getSwappable().toString()
            "get_transaction" -> transactionExplorer.getTransaction(args).toStringIfExist()
            "get_number_of_transactions" -> transactionExplorer.getNumberOfTransactions().toString()
            "get_number_of_transactions_by_address" -> transactionExplorer.getNumberOfTransactionsByAddress(args).toString()
            else -> throw CommandNotFoundException()
        }
    }
}