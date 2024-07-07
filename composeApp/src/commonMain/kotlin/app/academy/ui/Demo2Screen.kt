package app.academy.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.academy.domain.DemoViewModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlin.random.Random

class Demo2Screen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: DemoViewModel = rememberScreenModel {
                DemoViewModel()
            }
        val uiState = viewModel.uiState.toList()

        Column { // Use a Column to display multiple items
            Text("Click me to simulate the bug ${uiState.size}", modifier = Modifier.clickable {
                viewModel.addToList(Random.nextInt())
            })

            // Display the list items
            uiState.forEach { item ->
                Text(text = item)
            }
        }
    }
}
