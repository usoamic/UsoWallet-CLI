package io.usoamic.cli.core

import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.util.ValidateUtil
import io.usoamic.cli.util.getOrEmpty
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.enum.NoteVisibility
import io.usoamic.usoamickotlin.model.Note
import java.math.BigInteger
import javax.inject.Inject

class Notes @Inject constructor(private val usoamic: Usoamic) {
    fun addPublicNote(args: List<String>): String = addNote( NoteVisibility.PUBLIC, args)

    fun addUnlistedNote(args: List<String>): String = addNote(NoteVisibility.UNLISTED, args)

    fun getNumberOfPublicNotes(): BigInteger {
        usoamic.getNumberOfPublicNotes()?.let {
            return it
        }
        throw ContractNullPointerException()
    }

    fun getNumberOfNotesByAuthor(args: List<String>): BigInteger {
        val author = args.getOrEmpty(1)
        ValidateUtil.validateAddress(author)
        usoamic.getNumberOfNotesByAuthor(author)?.let {
            return it
        }
        throw ContractNullPointerException()
    }

    fun getNoteByAuthor(args: List<String>): Note {
        val author = args.getOrEmpty(1)
        val noteId = args.getOrEmpty(2)
        ValidateUtil.validateAddress(author)
            .validateId(noteId)
        return usoamic.getNoteByAuthor(author, noteId.toBigInteger())
    }

    fun getNote(args: List<String>): Note {
        val noteRefId = args.getOrEmpty(2)
        ValidateUtil.validateId(noteRefId)
        return usoamic.getNote(noteRefId.toBigInteger())
    }

    private fun addNote(noteVisibility: NoteVisibility, args: List<String>): String {
        val password = args.getOrEmpty(1)
        val content = args.getOrEmpty(2)
        ValidateUtil.validatePassword(password)
            .validateDescription(content)
        return when(noteVisibility) {
            NoteVisibility.PUBLIC -> usoamic.addPublicNote(password, content)
            NoteVisibility.UNLISTED -> usoamic.addUnlistedNote(password, content)
        }
    }
}