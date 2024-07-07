package app.academy.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import app.academy.di.AppModule
import app.academy.domain.NoteDetailViewModel
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import co.touchlab.kermit.Logger

class NoteDetailScreen(private val appModule: AppModule, private val noteId: String? = null) :
    Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val canPop = navigator.canPop
        val viewModel: NoteDetailViewModel = navigator.rememberNavigatorScreenModel {
                NoteDetailViewModel(
                    currentNoteId = noteId,
                    notesRepository = appModule.provideNotesRepository(),
                    coroutineScope = coroutineScope
                )
            }

        DisposableEffect(Unit){
            Logger.d("ComposeLife") { "Note Detail is running ${viewModel.hashCode()} $canPop"}
            onDispose {
                Logger.d("ComposeLife") { "Note Detail is disposed ${viewModel.hashCode()} $canPop"}
            }
        }
        val noteTitle by viewModel.titleTextStream.collectAsState()
        val noteContent by viewModel.contentTextStream.collectAsState()
        NoteDetailScreenContent(
            noteTitle = noteTitle,
            noteContent = noteContent,
            onNoteTitleChange = viewModel::onTitleChange,
            onNoteContentChange = viewModel::onContentChange,
            onBackButtonClick = {
                navigator.pop()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreenContent(
    modifier: Modifier = Modifier,
    noteTitle: String,
    noteContent: String,
    onNoteTitleChange: (String) -> Unit,
    onNoteContentChange: (String) -> Unit,
    onBackButtonClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    // without adding scrollState modifier, the "note content" TextField inside this composable
    // will scroll independent of the screen. This behavior is undesirable, hence use scroll state.
    Column(modifier = modifier.verticalScroll(scrollState)) {
        IconButton(
            modifier = Modifier.statusBarsPadding(),
            onClick = onBackButtonClick,
            content = { Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null) }
        )
        NoteDetailScreenBasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = noteTitle,
            textStyle = MaterialTheme.typography.displaySmall,
            onValueChange = onNoteTitleChange,
            placeholderText = "Title"
        )
        NoteDetailScreenBasicTextField(
            modifier = Modifier.fillMaxSize(),
            value = noteContent,
            onValueChange = onNoteContentChange,
            textStyle = MaterialTheme.typography.titleLarge,
            placeholderText = "Note"
        )
    }
}

@ExperimentalMaterial3Api
@Composable
private fun NoteDetailScreenBasicTextField(
    value: String,
    modifier: Modifier = Modifier,
    placeholderText: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onValueChange: (String) -> Unit,
) {
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    BasicTextField(
        modifier = modifier,
        interactionSource = interactionSource,
        value = value,
        textStyle = mergedTextStyle,
        onValueChange = onValueChange,
        cursorBrush = SolidColor(textColor),
        decorationBox = {
            TextFieldDefaults.DecorationBox(
                interactionSource = interactionSource,
                value = value,
                innerTextField = it,
                enabled = true,
                singleLine = false,
                visualTransformation = VisualTransformation.None,
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(0.7f),
                        text = placeholderText,
                        style = textStyle
                    )
                },
                container = {}
            )
        }
    )
}
