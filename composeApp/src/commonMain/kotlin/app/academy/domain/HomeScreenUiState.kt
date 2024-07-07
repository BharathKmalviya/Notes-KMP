package app.academy.domain

import app.academy.model.Note

/**
 * The UI state for the home screen.
 */
data class HomeScreenUiState(
    val isLoadingSavedNotes: Boolean = false,
    val isLoadingSearchResults: Boolean = false,
    val savedNotes: List<Note> = emptyList(),
    val searchResults: List<Note> = emptyList()
)
