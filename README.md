# UsoWallet-CLI
## Commands:
* import_mnemonic_phrase <PASSWORD> <MNEMONIC_PHRASE>
* import_private_key <PASSWORD> <PRIVATE_KEY>
* create_mnemonic_phrase
* create_private_key
* get_address
* get_eth_balance
* get_uso_balance
* eth_transfer <PASSWORD> <TO> <VALUE>
* uso_transfer <PASSWORD> <TO> <VALUE>
* get_version
* burn_uso <PASSWORD> <VALUE>
* uso_balance_of <ADDRESS>
* eth_balance_of <ADDRESS>
* get_supply
* get_contract_version
* add_idea <PASSWORD> <DESCRIPTION>
* get_idea <IDEA_REF_ID>
* get_idea_by_author <ADDRESS> <IDEA_ID>
* support_idea <PASSWORD> <IDEA_REF_ID> <COMMENT>
* abstain_idea <PASSWORD> <IDEA_REF_ID> <COMMENT>
* against_idea <PASSWORD> <IDEA_REF_ID> <COMMENT>
* get_vote <IDEA_REF_ID> <VOTE_REF_ID>
* get_vote_by_voter <VOTER> <VOTE_ID>
* get_number_of_ideas_by_author <AUTHOR>
* get_number_of_ideas
* get_number_of_votes_by_voter <VOTER>
* add_public_note <PASSWORD> <CONTENT>
* add_unlisted_note <PASSWORD> <CONTENT>
* get_number_of_public_notes
* get_number_of_notes_by_author <AUTHOR>
* get_note_by_author <AUTHOR> <NOTE_ID>
* get_note <NOTE_REF_ID>
* set_frozen <FROZEN>
* set_owner <OWNER>
* make_purchase <PASSWORD> <APP_ID> <PURCHASE_ID> <COST>
* get_purchase_by_address <ADDRESS> <ID>
* get_number_of_purchases_by_address <ADDRESS>
* withdraw_eth <PASSWORD> <VALUE>
* burn_swap <PASSWORD> <VALUE>
* set_swap_rate <PASSWORD> <SWAP_RATE>
* set_swappable <PASSWORD> <SWAPPABLE>
* get_swap_balance
* get_swap_rate
* get_swappable
* get_transaction <TX_ID>
* get_number_of_transactions
* get_number_of_transactions_by_address <ADDRESS>