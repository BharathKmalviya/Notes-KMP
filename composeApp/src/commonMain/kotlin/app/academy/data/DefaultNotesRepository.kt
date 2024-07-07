package app.academy.data

import app.academy.data.local.datasource.LocalNotesDataSource
import app.academy.model.Note
import app.academy.model.toNote
import app.academy.model.toSavedNoteEntity
import app.academy.notes.database.SavedNoteEntity
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultNotesRepository(
    private val localNotesDataSource: LocalNotesDataSource
) : NotesRepository {

    override val savedNotesStream: Flow<List<Note>> =
        localNotesDataSource.savedNotesStream.map { savedNoteEntities ->
            savedNoteEntities.map { savedNoteEntity: SavedNoteEntity -> savedNoteEntity.toNote() }
        }

    override suspend fun saveNote(note: Note) {
        localNotesDataSource.saveNote(note.toSavedNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        localNotesDataSource.markNoteAsDeleted(note.id)
    }

    override suspend fun deleteAllNotesMarkedAsDeleted() {
        localNotesDataSource.deleteAllNotesMarkedAsDeleted()
    }

    override fun searchNotes(query: String): List<Note> {
       return localNotesDataSource.searchNotes(query)
           .map { savedNoteEntity ->
               savedNoteEntity.toNote()
           }
    }
}
