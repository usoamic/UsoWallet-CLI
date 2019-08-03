package io.usoamic.cli.core

class Help {
    private var list = ArrayList<String>()

    init {
        list.apply {
            add("import_mnemonic_phrase <PASSWORD> <MNEMONIC_PHRASE>")
            add("import_private_key <PASSWORD> <PRIVATE_KEY>")
            add("create_mnemonic_phrase")
            add("create_private_key")
            add("get_address")
            add("get_eth_balance")
            add("get_uso_balance")
            add("eth_transfer <PASSWORD> <TO> <VALUE>")
            add("uso_transfer <PASSWORD> <TO> <VALUE>")
            add("get_version")
            add("burn_uso <PASSWORD> <VALUE>")
            add("uso_balance_of <ADDRESS>")
            add("eth_balance_of <ADDRESS>")
            add("get_supply")
            add("get_contract_version")
            add("add_idea <PASSWORD> <DESCRIPTION>")
            add("get_idea <IDEA_REF_ID>")
            add("get_idea_by_author <ADDRESS> <IDEA_ID>")
            add("support_idea <PASSWORD> <IDEA_REF_ID> <COMMENT>")
            add("abstain_idea <PASSWORD> <IDEA_REF_ID> <COMMENT>")
            add("against_idea <PASSWORD> <IDEA_REF_ID> <COMMENT>")
            add("get_vote <IDEA_REF_ID> <VOTE_REF_ID>")
            add("get_vote_by_voter <VOTER> <VOTE_ID>")
            add("get_number_of_ideas_by_author <AUTHOR>")
            add("get_number_of_ideas")
            add("get_number_of_votes_by_voter <VOTER>")
            add("add_public_note <PASSWORD> <CONTENT>")
            add("add_unlisted_note <PASSWORD> <CONTENT>")
            add("get_number_of_public_notes")
            add("get_number_of_notes_by_author <AUTHOR>")
            add("get_note_by_author <AUTHOR> <NOTE_ID>")
            add("get_note <NOTE_REF_ID>")
            add("set_frozen <FROZEN>")
            add("set_owner <OWNER>")
            add("make_purchase <PASSWORD> <APP_ID> <PURCHASE_ID> <COST>")
            add("get_purchase_by_address <ADDRESS> <ID>")
            add("get_number_of_purchases_by_address <ADDRESS>")
            add("withdraw_eth <PASSWORD> <VALUE>")
            add("burn_swap <PASSWORD> <VALUE>")
            add("set_swap_rate <PASSWORD> <SWAP_RATE>")
            add("set_swappable <PASSWORD> <SWAPPABLE>")
            add("get_swap_balance")
            add("get_swap_rate")
            add("get_swappable")
            add("get_transaction <TX_ID>")
            add("get_number_of_transactions")
            add("get_number_of_transactions_by_address <ADDRESS>")
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        var id = 0
        val lastId = (list.size - 1)
        list.forEach {
            builder.append(it).apply{
                if(id < lastId) {
                    append("\n")
                }
            }
            id++
        }
        return builder.toString()
    }
}