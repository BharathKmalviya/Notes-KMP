package app.academy.ui

import androidx.compose.runtime.Composable
import app.academy.di.AppModule
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

@Composable
fun NotesAppNavigation(
    appModule: AppModule,
) {
    Navigator(screen = HomeScreen(appModule)) {
        SlideTransition(navigator = it)
    }
}
