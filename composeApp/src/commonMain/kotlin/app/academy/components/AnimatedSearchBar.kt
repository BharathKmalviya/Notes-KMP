package app.academy.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.academy.model.Note
import app.academy.ui.NoteDetailScreen
import app.academy.utils.getWindowSize

/**
 * Stateless searchBar
 */
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun AnimatedSearchBar(
    query: String,
    isSearchBarActive: Boolean,
    onQueryChange: (String) -> Unit,
    onClearSearchQueryButtonClick: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onBackButtonClick: () -> Unit,
    onNoteDismissed: (Note) -> Unit,
    onNoteItemClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
    searchResults: List<Note>,
    suggestionsForQuery: List<Note>
) {
    val leadingIcon = @Composable {
        AnimatedContent(targetState = isSearchBarActive, label = "") { isActive ->
            if (isActive) {
                IconButton(onClick = onBackButtonClick) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            } else {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            }
        }
    }

    val trailingIcon = @Composable {
        AnimatedContent(targetState = isSearchBarActive, label = "") { isActive ->
            if (isActive) {
                IconButton(
                    onClick = onClearSearchQueryButtonClick,
                    content = { Icon(imageVector = Icons.Filled.Close, contentDescription = null) }
                )
            }
        }
    }
    val windowSizeWithoutInsets = getWindowSize()
    Column(modifier = modifier) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .sizeIn(
                    // need sizeIn modifier to prevent exception being thrown about size being set to infinity when search bar is expanded
                    maxWidth = windowSizeWithoutInsets.width,
                    maxHeight = windowSizeWithoutInsets.height
                ),
            query = query,
            onQueryChange = onQueryChange,
            onSearch = {
                // no need for this callback because this app uses instant search
            },
            active = isSearchBarActive,
            onActiveChange = onActiveChange,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            placeholder = { Text(text = "Search notes") },
        ) {
            if (suggestionsForQuery.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    NoteItems(
                        notes = suggestionsForQuery,
                        onClick = onNoteItemClick,
                        onDismissed = onNoteDismissed
                    )
                }
            }
            else {
                LazyColumn {
                    NoteItems(searchResults, onClick = {

                    }) {
                        //onDismissed
                    }
                }
            }
        }
    }
}
