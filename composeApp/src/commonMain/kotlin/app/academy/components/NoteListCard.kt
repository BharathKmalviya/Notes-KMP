package app.academy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.academy.model.Note

/**
 * A composable that displays the information of a [Note], in a card. Some of the contents
 * might get truncated if the content of the [Note] is too long. Mainly meant to be used in
 * a list, such as a [LazyColumn] or a [LazyRow].
 *
 * @param modifier The modifier to be applied to the card.
 * @param savedNote The note to be displayed.
 * @param onClick The click listener for the card.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteListCard(
    modifier: Modifier = Modifier,
    savedNote: Note,
    onClick: () -> Unit
) {
    Card(modifier = modifier, onClick = onClick) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = savedNote.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                maxLines = 3
            )
            Text(
                text = savedNote.content,
                maxLines = 3,
                minLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
