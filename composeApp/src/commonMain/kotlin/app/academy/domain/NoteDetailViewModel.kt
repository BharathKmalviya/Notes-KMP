package app.academy.domain

import app.academy.data.NotesRepository
import app.academy.model.Note
import app.academy.utils.UUID
import app.academy.utils.asNativeStateFlow
import cafe.adriel.voyager.core.model.ScreenModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@OptIn(FlowPreview::class)
class NoteDetailViewModel(
    private val currentNoteId: String?,
    private val notesRepository: NotesRepository,
    coroutineScope: CoroutineScope?
) : ScreenModel {

    private val viewModelScope =
        coroutineScope ?: CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _titleText = MutableStateFlow("")
    val titleTextStream = _titleText.asNativeStateFlow()

    private val _contentText = MutableStateFlow("")
    val contentTextStream = _contentText.asNativeStateFlow()

    private lateinit var currentNote: Note


    init {
        Logger.d("NotesDetails"){"currentNoteId: $currentNoteId"}
        viewModelScope.launch {
            currentNote = getOrCreateNoteWithId(currentNoteId ?: UUID.randomUUIDString())
            _titleText.update { currentNote.title }
            _contentText.update { currentNote.content }
            val debounceTimeout = 200L
            combine(
                _titleText.debounce(timeoutMillis = debounceTimeout).distinctUntilChanged(),
                _contentText.debounce(timeoutMillis = debounceTimeout).distinctUntilChanged()
            ) { updatedTitleText, updatedContentText ->
                Logger.d("NotesDetails"){"CurrentNoteIn NDVM: $currentNote"}
                // remove note from database, if note is blank
                if (updatedTitleText.isBlank() && updatedContentText.isBlank()) {
                    notesRepository.deleteNote(currentNote)
                    return@combine
                }
                val updatedNote = currentNote.copy(
                    title = updatedTitleText,
                    content = updatedContentText
                )
                Logger.d("NotesDetails") { "Saving Note $updatedNote" }
                notesRepository.saveNote(updatedNote)
            }.launchIn(this)
        }
    }

    fun onTitleChange(newTitle: String) {
        _titleText.update { newTitle }
    }

    fun onContentChange(newContent: String) {
        _contentText.update { newContent }
    }

    private suspend fun getOrCreateNoteWithId(id: String): Note {
        val savedNotes = notesRepository.savedNotesStream.first()
        Logger.d("NotesDetails"){"Fetched notes in getOrCreateNoteWithId::$savedNotes"}
        val matchingNote = savedNotes.firstOrNull { it.id == id }
        Logger.d("NotesDetails"){"matchingNote::$matchingNote"}
        if (matchingNote != null) return matchingNote
        return Note(
            id = id,
            title = "",
            content = "",
            createdAtTimestampMillis = Clock.System.now().toEpochMilliseconds(),
            isDeleted = false
        ).also {
            Logger.d("NotesDetails"){"Saving or creating new note: $it"}
        }
    }

}
