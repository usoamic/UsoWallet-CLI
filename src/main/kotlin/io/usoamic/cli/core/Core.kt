package io.usoamic.cli.core

import io.usoamic.cli.exception.CommandNotFoundException
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.cli.util.removeQuotes
import io.usoamic.cli.util.toStringIfExist
import javax.inject.Inject

class Core @Inject constructor(
    private val accountManager: AccountManager,
    private val help: Help,
    private val ideas: Ideas,
    private val notes: Notes,
    private val owner: Owner,
    private val purchases: Purchases,
    private val swap: Swap,
    private val transactionExplorer: TransactionExplorer,
    private val usoamic: Usoamic
) {
    fun getResponse(line: String): String {
        val args = line.split(Regex(" (?=(?:[^\\\']*\\\'[^\\\']*\\\')*[^\\\']*\$)"))
                                   .map { it.removeQuotes() }

        return when (args.getOrEmpty(0)) {
            "help" -> help.toString()
            "import_mnemonic_phrase" -> accountManager.importMnemonicPhrase(args)
            "import_private_key" -> accountManager.importPrivateKey(args)
            "create_mnemonic_phrase" -> accountManager.createMnemonicPhrase()
            "create_private_key" -> accountManager.createPrivateKey()
            "get_address" -> usoamic.getAddress()
            "get_eth_balance" -> usoamic.getEthBalance()
            "get_uso_balance" -> usoamic.getUsoBalance()
            "eth_transfer" -> usoamic.transferEth(args)
            "uso_transfer" -> usoamic.transferUso(args)
            "burn_uso" -> usoamic.burnUso(args)
            "uso_balance_of" -> usoamic.usoBalanceOf(args).toString()
            "eth_balance_of" -> usoamic.ethBalanceOf(args).toString()
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
            "get_number_of_purchases_by_address" -> purchases.getNumberOfPurchasesByAddress(args).toString()
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