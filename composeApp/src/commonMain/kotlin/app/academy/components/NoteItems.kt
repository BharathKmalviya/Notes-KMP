package app.academy.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.academy.model.Note

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.NoteItems(
    notes: List<Note>,
    onClick: (Note) -> Unit,
    onDismissed: (Note) -> Unit
) {
    items(items = notes, key = { it.id }) {
        SwipeableNoteListCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .animateItemPlacement(),
            savedNote = it,
            onClick = { onClick(it) },
            onDismissed = { onDismissed(it) }
        )
    }
}
