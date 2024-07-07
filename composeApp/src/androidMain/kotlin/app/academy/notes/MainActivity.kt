package app.academy.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.academy.NotesAppApplication
import app.academy.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = this.application as NotesAppApplication
        val appModule = application.appModule
        setContent {
            App(appModule)
        }
    }
}
