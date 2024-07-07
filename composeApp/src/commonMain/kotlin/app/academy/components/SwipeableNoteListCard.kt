package app.academy.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.academy.model.Note

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableNoteListCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onDismissed: () -> Unit,
    savedNote: Note,
) {
    val dismissState = remember(savedNote) {
        DismissState(
            initialValue = DismissValue.Default,
            confirmStateChange = {
                if (it != DismissValue.DismissedToStart) return@DismissState false
                onDismissed()
                true
            }
        )
    }

    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        background = {},
        dismissContent = {
            NoteListCard(
                modifier = Modifier.fillMaxWidth(),
                savedNote = savedNote,
                onClick = onClick
            )
        },
        directions = setOf(DismissDirection.EndToStart)
    )
}
