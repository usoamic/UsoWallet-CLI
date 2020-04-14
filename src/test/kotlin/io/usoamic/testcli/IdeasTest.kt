package io.usoamic.testcli

import io.usoamic.cli.core.Core
import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.testcli.other.TestConfig
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.VoteType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import javax.inject.Inject
import kotlin.random.Random

class IdeasTest {
    @Inject
    lateinit var core: Core
    @Inject
    lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun addIdeaTest() {
        val address = core.getResponse("get_address")
        val description = "Idea #${Random.nextInt()}"
        val numberOfIdeas = core.getResponse("get_number_of_ideas")
        val txHash = core.getResponse("add_idea ${TestConfig.PASSWORD} '$description'")
        usoamic.waitTransactionReceipt(txHash) {
            val idea = core.getResponse("get_idea $numberOfIdeas")
            assert(idea.contains("isExist=true"))
            assert(idea.contains("ideaRefId=$numberOfIdeas"))
            assert(idea.contains("author=$address"))
            assert(idea.contains("description=$description"))
        }
    }

    @Test
    fun getIdeaTest() {
        val numberOfIdeas = core.getResponse("get_number_of_ideas")
        if (numberOfIdeas.toBigInteger() > BigInteger.ZERO) {
            val idea = core.getResponse("get_idea ${BigInteger.ZERO}")
            assert(idea.contains("isExist=true"))
        }

        assertThrows<ObjectNotFoundException> {
            core.getResponse("get_idea $numberOfIdeas")
        }
    }

    @Test
    fun getIdeasByAuthorTest() {
        val address = core.getResponse("get_address")
        val numberOfIdeas = core.getResponse("get_number_of_ideas_by_author $address")
        if (numberOfIdeas.toBigInteger() > BigInteger.ZERO) {
            val idea = core.getResponse("get_idea_by_author $address ${BigInteger.ZERO}")
            assert(idea.contains("isExist=true"))
        }

        assertThrows<ObjectNotFoundException> {
            core.getResponse("get_idea_by_author $address $numberOfIdeas")
        }
    }

    @Test
    fun getVoteByVoterTest() {
        val address = core.getResponse("get_address")
        val numberOfVotes = core.getResponse("get_number_of_votes_by_voter $address")

        if (numberOfVotes.toBigInteger() > BigInteger.ZERO) {
            val vote = core.getResponse("get_vote_by_voter $address ${BigInteger.ZERO}")
            assert(vote.contains("isExist=true"))
            assert(vote.contains("voteId=${BigInteger.ZERO}"))
        }

        assertThrows<ObjectNotFoundException> {
            core.getResponse("get_vote_by_voter $address $numberOfVotes")
        }
    }

    @Test
    fun getNumberOfIdeasByAuthorTest() {
        val address = core.getResponse("get_address")
        val numberOfIdeas = core.getResponse("get_number_of_ideas_by_author $address")
        assert(numberOfIdeas.toBigInteger() >= BigInteger.ZERO)
    }

    @Test
    fun getNumberOfIdeasTest() {
        val numberOfIdeas = core.getResponse("get_number_of_ideas")
        assert(numberOfIdeas.toBigInteger() >= BigInteger.ZERO)
    }

    @Test
    fun getNumberOfVotesByVoterTest() {
        val address = core.getResponse("get_address")
        val numberOfVotes = core.getResponse("get_number_of_votes_by_voter $address")
        assert(numberOfVotes.toBigInteger() >= BigInteger.ZERO)
    }

    @Test
    fun getVoteTest() {
        val address = core.getResponse("get_address")

        val ideaId = BigInteger.ZERO
        val idea = core.getResponse("get_idea $ideaId")
        val numberOfParticipants = idea.split("numberOfParticipants=")[1].split(")")[0].toBigInteger()

        val callback = fun(address: String, ideaId: BigInteger, voteId: BigInteger) {
            val vote = core.getResponse("get_vote $ideaId $voteId")
            assertVote(vote, ideaId, voteId, address)
        }

        if (numberOfParticipants == BigInteger.ZERO) {
            voteForIdea(VoteType.SUPPORT, ideaId, address) {
                callback(address, ideaId, numberOfParticipants)
            }
        } else {
            callback(address, ideaId, (numberOfParticipants - BigInteger.ONE))
        }
    }

    @Test
    fun supportIdeaTest() {
        voteForNewIdea(VoteType.SUPPORT)
    }

    @Test
    fun abstainIdeaTest() {
        voteForNewIdea(VoteType.ABSTAIN)
    }

    @Test
    fun againstIdeaTest() {
        voteForNewIdea(VoteType.AGAINST)
    }

    private fun voteForNewIdea(voteType: VoteType) {
        val address = core.getResponse("get_address")
        val numberOfIdeas = core.getResponse("get_number_of_ideas")
        val ideaTxHash = core.getResponse("add_idea ${TestConfig.PASSWORD} 'Idea #${Random.nextInt()}'")

        usoamic.waitTransactionReceipt(ideaTxHash) {
            voteForIdea(voteType, numberOfIdeas.toBigInteger(), address) { }
        }
    }

    private fun voteForIdea(voteType: VoteType, ideaId: BigInteger, address: String, callback: (txHash: String) -> Unit) {
        val comment = "Comment #${Random.nextInt()}"
        val vType = when (voteType) {
            VoteType.SUPPORT -> "support_idea"
            VoteType.AGAINST -> "against_idea"
            VoteType.ABSTAIN -> "abstain_idea"
        }

        val voteTxHash = core.getResponse("$vType ${TestConfig.PASSWORD} $ideaId '$comment'")


        usoamic.waitTransactionReceipt(voteTxHash) {
            callback(voteTxHash)
            val voteId = BigInteger.ZERO
            val vote = core.getResponse("get_vote $ideaId $voteId")
            assertVote(vote, ideaId, voteId, address)
            assert(vote.contains("voteType=$voteType"))
            assert(vote.contains("comment=$comment"))
        }
    }

    private fun assertVote(vote: String, ideaId: BigInteger, voteRefId: BigInteger, address: String) {
        assert(vote.contains("isExist=true"))
        assert(vote.contains("voter=$address"))
        assert(vote.contains("ideaRefId=$ideaId"))
        assert(vote.contains("voteRefId=$voteRefId"))

    }
}