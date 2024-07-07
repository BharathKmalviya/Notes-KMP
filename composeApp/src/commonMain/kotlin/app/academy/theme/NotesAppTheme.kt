package app.academy.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun NotesAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}
