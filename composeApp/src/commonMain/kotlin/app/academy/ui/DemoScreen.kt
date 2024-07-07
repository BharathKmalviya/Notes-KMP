package app.academy.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.academy.domain.DemoViewModel
import app.architect.notes.utils.Greeting
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import co.touchlab.kermit.Logger
import kmpnotes.composeapp.generated.resources.Res
import kmpnotes.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

object DemoScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: DemoViewModel = rememberScreenModel {
                DemoViewModel()
            }
        var showContent by remember { mutableStateOf(true) }
        val uiState = viewModel.uiState.toList()
        Logger.d("CompLifeBug") { "Why?:$uiState" }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting =
                    remember { Greeting().greet(viewModel.getGreetingPrefixFromServer()) }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text(
                        "Compose: $greeting $uiState",
                        Modifier.clickable { navigator.push(Demo2Screen()) })
                    Text(Greeting().hello())
                }
            }
        }
    }

}
