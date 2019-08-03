package io.usoamic.testcli

import io.usoamic.cli.core.Core
import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.testcli.other.TestConfig
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.enum.NoteVisibility
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import javax.inject.Inject
import kotlin.random.Random

class NotesTest {
    @Inject
    lateinit var core: Core
    @Inject
    lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun addPublicNoteTest() {
        val noteContent = "My note #${Random.nextInt()}"
        val numberOfPublicNotes = core.getResponse("get_number_of_public_notes")
        val txHash = core.getResponse("add_public_note ${TestConfig.PASSWORD} '$noteContent'")
        usoamic.waitTransactionReceipt(txHash) {
            val note = core.getResponse("get_note $numberOfPublicNotes")
            println(note)
            assert(note.contains("isExist=true"))
            assert(note.contains("content=$noteContent"))
            assert(note.contains("visibility=${NoteVisibility.PUBLIC}"))
        }
    }

    @Test
    fun addUnlistedNoteTest() {
        val noteContent = "My unlisted note #${Random.nextInt()}"
        val numberOfNotes = core.getResponse("get_number_of_notes_by_author ${TestConfig.DEFAULT_ADDRESS}")
        val txHash = core.getResponse("add_unlisted_note ${TestConfig.PASSWORD} $noteContent")
        usoamic.waitTransactionReceipt(txHash) {
            val note = core.getResponse("get_note_by_author ${TestConfig.DEFAULT_ADDRESS} $numberOfNotes")
            assert(note.contains("isExist=true"))
            assert(note.contains("content=$noteContent"))
            assert(note.contains("visibility=${NoteVisibility.UNLISTED}"))
        }
    }

    @Test
    fun getNumberOfPublicNotesTest() {
        val numberOfNotes = core.getResponse("get_number_of_public_notes")
        assert(numberOfNotes.toBigInteger() >= BigInteger.ZERO)
    }

    @Test
    fun getNumberOfNotesByAuthorTest() {
        val numberOfNotes = core.getResponse("get_number_of_notes_by_author ${TestConfig.DEFAULT_ADDRESS}")
        assert(numberOfNotes.toBigInteger() >= BigInteger.ZERO)
    }

    @Test
    fun getNoteByAuthorTest() {
        val numberOfNotes = core.getResponse("get_number_of_notes_by_author ${TestConfig.DEFAULT_ADDRESS}")
        if (numberOfNotes.toBigInteger() > BigInteger.ZERO) {
            val note = core.getResponse("get_note_by_author ${TestConfig.DEFAULT_ADDRESS} ${BigInteger.ZERO}")
            assert(note.contains("isExist=true"))
        }

        assertThrows<ObjectNotFoundException> {
            core.getResponse("get_note_by_author ${TestConfig.DEFAULT_ADDRESS} $numberOfNotes")
        }

    }

    @Test
    fun getNoteTest() {
        val numberOfNotes = core.getResponse("get_number_of_public_notes")
        if (numberOfNotes.toBigInteger() > BigInteger.ZERO) {
            val note = core.getResponse("get_note ${BigInteger.ZERO}")
            assert(note.contains("isExist=true"))
        }

        assertThrows<ObjectNotFoundException> {
            core.getResponse("get_note $numberOfNotes")
        }
    }
}