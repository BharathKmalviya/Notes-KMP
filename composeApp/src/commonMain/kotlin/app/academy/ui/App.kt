package app.academy.ui

import androidx.compose.runtime.Composable
import app.academy.di.AppModule
import app.academy.theme.NotesAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(appModule: AppModule) {
    NotesAppTheme {
        NotesAppNavigation(appModule)
    }
}
