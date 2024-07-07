package app.academy.domain

import app.academy.data.NotesRepository
import app.academy.di.AppModule
import app.academy.model.Note
import app.academy.utils.asNativeStateFlow
import cafe.adriel.voyager.core.model.ScreenModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    coroutineScope: CoroutineScope?,
    appModule: AppModule
) : ScreenModel {

    private val viewModelScope = coroutineScope ?: MainScope()
    private val notesRepository: NotesRepository = appModule.provideNotesRepository()
    private val defaultDispatcher: CoroutineDispatcher =
        appModule.provideDispatchersProvider().defaultDispatcher

    init {
        Logger.d("NotesBug") { " Init Called for this viewmodel ${this.hashCode()}" }
    }

    /**
     * The current [HomeScreenUiState]
     */
    private val _uiState = MutableStateFlow(HomeScreenUiState(isLoadingSavedNotes = true))
    val uiState = _uiState.asNativeStateFlow()

    private val currentSearchText = MutableStateFlow("")
    private var recentlyDeletedNote: Note? = null

    /**
     * Searches for notes with titles or contents containing the given [searchText].
     */
    fun search(searchText: String) {
        Logger.d("NotesSearch") { "Search updated :: $searchText" }
        currentSearchText.value = searchText
    }

    fun fetchNotes() {
        Logger.d("NotesVMLife") { "fetchNotes called" }
        //while DB updates , there is no listener to that state
        // read about stateful and stateless
        notesRepository.savedNotesStream.onEach { savedNotesList ->
            Logger.d("NotesBug") { "Update: ${savedNotesList.map { it.title }}" }
            _uiState.update { it.copy(isLoadingSavedNotes = false, savedNotes = savedNotesList) }
        }.launchIn(viewModelScope)
        Logger.d("NotesBug") { "Current notes despite calling fetch notes is ${_uiState.value.savedNotes.size}" }
        combineSearchAndSavedNotes()
    }

    private fun combineSearchAndSavedNotes() {
        combine(
            uiState,
            currentSearchText.debounce { 200L }
        ) { updatedUiState, searchText ->
            Logger.d("NotesSearch") { "Search debounced :: $searchText" }
            Logger.d("NotesBug") { " this is inside combine :: is ${updatedUiState.savedNotes.size}" }
            val savedNotes = updatedUiState.savedNotes
            // filtering notes with titles containing the search text
            _uiState.update { it.copy(isLoadingSearchResults = true) }
            val notesWithTitleContainingSearchText = savedNotes.filter {
                it.title.contains(searchText, ignoreCase = true)
            }
            _uiState.update { it.copy(searchResults = notesWithTitleContainingSearchText) }
            Logger.d("NotesSearch") { "Search results :: $notesWithTitleContainingSearchText" }

            // filtering notes with the content containing the search text.
            // Since this is slower than the previous filtering operation above,
            // update ui state independently
            val notesWithContentContainingSearchText = savedNotes.filter {
                it.content.contains(searchText, ignoreCase = true)
            }
            _uiState.update {
                it.copy(searchResults = (it.searchResults + notesWithContentContainingSearchText).distinct())
            }
            _uiState.update {
                it.copy(isLoadingSearchResults = false)
            }

        }.flowOn(defaultDispatcher).launchIn(viewModelScope)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
            recentlyDeletedNote = note
        }
    }

    fun restoreRecentlyDeletedNote() {
        viewModelScope.launch { recentlyDeletedNote?.let { notesRepository.saveNote(it) } }
    }

    override fun onDispose() {
        super.onDispose()
        Logger.d("NotesBug") { " OnDispose called for ${this.hashCode()}" }
    }

}
