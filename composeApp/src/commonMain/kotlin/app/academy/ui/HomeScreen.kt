package app.academy.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.academy.components.AnimatedSearchBar
import app.academy.components.NoteItems
import app.academy.di.AppModule
import app.academy.domain.HomeViewModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import co.touchlab.kermit.Logger
import kotlin.time.measureTime

class HomeScreen(private val appModule: AppModule) : Screen {
    lateinit var viewModel: HomeViewModel

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val canPop = navigator.canPop
        val coroutineScope = rememberCoroutineScope()
        val measureInTime = measureTime {
            viewModel = rememberScreenModel {
                HomeViewModel(
                    coroutineScope = coroutineScope,
                    appModule = appModule
                )
            }
        }
        Logger.d("NotesBug") { "measured time to init vm instance for ${viewModel.hashCode()} is $measureInTime $canPop" }
        val uiState by viewModel.uiState.collectAsState()
        DisposableEffect(Unit) {
            Logger.d("HomeScreen is running") { "HomeScreen is running ${viewModel.hashCode()} $canPop" }
            onDispose {
                Logger.d("NotesBug") { "HomeScreen is disposed ${uiState.savedNotes.size} $canPop" }
            }
        }
        var currentSearchQuery by remember { mutableStateOf("") }
        var isSearchBarActive by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            Logger.d("NotesBug") { "HomeScreen called fetchNotes $canPop" }
            viewModel.fetchNotes()
        }
        LaunchedEffect(uiState) {
            Logger.d("NotesBug") { "uiState updated $canPop" }
            Logger.d("NotesBug") { "uiState updated ${uiState.savedNotes.size}" }
        }
        Box(Modifier.fillMaxSize()) {
            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    // Do not add status bars padding to AnimatedSearchBar.
                    // If the padding is added, then the search bar wouldn't
                    // fill the entire screen when it is expanded.
                    AnimatedSearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        query = currentSearchQuery,
                        isSearchBarActive = isSearchBarActive,
                        onQueryChange = {
                            currentSearchQuery = it
                            viewModel.search(currentSearchQuery)
                        },
                        onBackButtonClick = {
                            currentSearchQuery = ""
                            viewModel.search(currentSearchQuery)
                            isSearchBarActive = false
                        },
                        onActiveChange = { isSearchBarActive = it },
                        onClearSearchQueryButtonClick = {
                            currentSearchQuery = ""
                            viewModel.search(currentSearchQuery)
                        },
                        suggestionsForQuery = emptyList(),// TODO fetch via search ui state
                        onNoteDismissed = {
                            //TODO note dismiss
                        },
                        searchResults = uiState.searchResults,
                        onNoteItemClick = { note ->
                            navigator.push(NoteDetailScreen(appModule, note.id))
                        }
                    )
                }

                NoteItems(uiState.savedNotes, onClick = {
                    navigator.push(NoteDetailScreen(appModule, it.id))
                }) {
                    //onDismissed
                }
            }
            androidx.compose.material3.FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(16.dp),
                onClick = {
                    navigator.push(NoteDetailScreen(appModule))
                },
                content = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) }
            )
        }
    }
}
