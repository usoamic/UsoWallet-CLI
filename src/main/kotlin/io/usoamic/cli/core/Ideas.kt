package io.usoamic.cli.core

import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.enum.VoteType
import io.usoamic.usoamickotlin.model.Idea
import io.usoamic.usoamickotlin.model.Vote
import java.math.BigInteger
import javax.inject.Inject

class Ideas {
    @Inject
    lateinit var usoamic: Usoamic

    fun addIdea(args: List<String>): String {
        val password = args.getOrEmpty(1)
        val description = args.getOrEmpty(2)
        ValidateUtil.validatePassword(password)
            .validateDescription(description)
        return usoamic.addIdea(password, description)
    }

    fun getIdea(args: List<String>): Idea {
        val ideaRefId = args.getOrEmpty(1)
        ValidateUtil.validateId(ideaRefId)
        return usoamic.getIdea(ideaRefId.toBigInteger())
    }

    fun getIdeasByAuthor(args: List<String>): Idea {
        val address = args.getOrEmpty(1)
        val ideaId = args.getOrEmpty(2)
        ValidateUtil.validateAddress(address)
            .validateId(ideaId)
        return usoamic.getIdeaByAuthor(address, ideaId.toBigInteger())
    }

    fun supportIdea(args: List<String>): String {
        return voteForIdea(VoteType.SUPPORT, args)
    }

    fun abstainIdea(args: List<String>): String {
        return voteForIdea(VoteType.ABSTAIN, args)
    }

    fun againstIdea(args: List<String>): String {
        return voteForIdea(VoteType.AGAINST, args)
    }

    fun getVote(args: List<String>): Vote {
        val ideaRefId = args.getOrEmpty(1)
        val voteRefId = args.getOrEmpty(2)
        ValidateUtil.validateIds(ideaRefId, voteRefId)
        return usoamic.getVote(ideaRefId.toBigInteger(), voteRefId.toBigInteger())
    }

    fun getVoteByVoter(args: List<String>): Vote {
        val voter = args.getOrEmpty(1)
        val voteId = args.getOrEmpty(2)

        ValidateUtil.validateAddress(voter)
            .validateId(voteId)

        return usoamic.getVoteByVoter(voter, voteId.toBigInteger())
    }

    fun getNumberOfIdeasByAuthor(args: List<String>): BigInteger {
        val author = args.getOrEmpty(1)
        ValidateUtil.validateAddress(author)
        usoamic.getNumberOfIdeasByAuthor(author)?.let {
            return it
        }
        throw ContractNullPointerException()
    }

    fun getNumberOfIdeas(): BigInteger {
        usoamic.getNumberOfIdeas()?.let {
            return it
        }
        throw ContractNullPointerException()
    }

    fun getNumberOfVotesByVoter(args: List<String>): BigInteger {
        val voter = args.getOrEmpty(1)
        ValidateUtil.validateAddress(voter)
        usoamic.getNumberOfVotesByVoter(voter)?.let {
            return it
        }
        throw ContractNullPointerException()
    }

    private fun voteForIdea(voteType: VoteType, args: List<String>): String {
        val password = args.getOrEmpty(1)
        val ideaRefId = args.getOrEmpty(2)
        val comment = args.getOrEmpty(3)

        ValidateUtil.validatePassword(password)
            .validateId(ideaRefId)
            .validateComment(comment)

        return when(voteType) {
            VoteType.SUPPORT -> usoamic.supportIdea(password, ideaRefId.toBigInteger(), comment)
            VoteType.ABSTAIN -> usoamic.abstainIdea(password, ideaRefId.toBigInteger(), comment)
            VoteType.AGAINST -> usoamic.againstIdea(password, ideaRefId.toBigInteger(), comment)
        }
    }
}