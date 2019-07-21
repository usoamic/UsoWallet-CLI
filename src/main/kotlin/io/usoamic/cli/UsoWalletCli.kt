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
                    val txHash = usoamic.transferEth(password, to, value.toBigInteger())
                    println(txHash)
                }
                "uso_transfer" -> {
                    val password = args.getOrEmpty(1)
                    val to = args.getOrEmpty(2)
                    val value = args.getOrZero(3)
                    ValidateUtil.validatePassword(password)
                                .validateAddress(to)
                                .validateTransferValue(value)
                    val txHash = usoamic.transferUso(password, to, value.toBigInteger())
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
                    val idea = usoamic.getIdeaByAuthor(address, ideaId.toBigInteger())
                    idea.printIfExist()
                }
                //Vote for idea
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
                "get_vote_by_voter" -> {
                    val voter = args.getOrEmpty(1)
                    val voteId = args.getOrEmpty(2)

                    ValidateUtil.validateAddress(voter)
                                .validateId(voteId)

                    val vote = usoamic.getVoteByVoter(voter, voteId.toBigInteger())
                    vote.printIfExist()
                }
                "get_number_of_ideas_by_author" -> {
                    val author = args.getOrEmpty(1)
                    ValidateUtil.validateAddress(author)
                    val numberOfIdeas = usoamic.getNumberOfIdeasByAuthor(author)
                    println(numberOfIdeas)
                }
                "get_number_of_ideas" -> {
                    val numberOfIdeas = usoamic.getNumberOfIdeas()
                    println(numberOfIdeas)
                }
                "get_number_of_votes_by_voter" -> {
                    val voter = args.getOrEmpty(1)
                    ValidateUtil.validateAddress(voter)
                    val numberOfVotes = usoamic.getNumberOfVotesByVoter(voter)
                    println(numberOfVotes)
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