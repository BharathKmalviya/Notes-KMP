import androidx.compose.ui.window.ComposeUIViewController
import app.academy.di.AppModule
import app.academy.ui.App

fun MainViewController() = ComposeUIViewController { App(AppModule()) }
