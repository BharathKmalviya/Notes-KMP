package app.academy.model

import app.academy.notes.database.SavedNoteEntity


/**
 * Data class representing a note.
 *
 * @property id The ID of the note.
 * @property title The title of the note.
 * @property content The content of the note.
 * @property isDeleted Whether the note is deleted or not.
 */
data class Note(
    val id: String,
    val title: String,
    val content: String,
    val createdAtTimestampMillis: Long,
    val isDeleted: Boolean
)

val dummyNotes = listOf(
    Note(
        id = "id1",
        title = "Dummy note 1",
        content = "This is a test note 1",
        createdAtTimestampMillis = 0L,
        isDeleted = false
    ),
    Note(
        id = "id2",
        title = "Dummy note 2",
        content = "This is a test note 2",
        createdAtTimestampMillis = 1L,
        isDeleted = false
    ),
    Note(
        id = "id3",
        title = "Dummy note 3",
        content = "This is a test note 3",
        createdAtTimestampMillis = 2L,
        isDeleted = false
    )
)


/**
 * Used to convert a [SavedNoteEntity] to a [Note].
 */
fun SavedNoteEntity.toNote(): Note {
    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAtTimestampMillis = createdAtTimestamp,
        isDeleted = this.isDeleted == 1L
    )
}

/*
 * Used to convert a [Note] to a [SavedNoteEntity].
 */
fun Note.toSavedNoteEntity(): SavedNoteEntity {
    return SavedNoteEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        createdAtTimestamp = createdAtTimestampMillis,
        isDeleted = if (this.isDeleted) 1 else 0
    )
}
