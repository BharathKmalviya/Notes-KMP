package app.academy.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIScreen

@Composable
@OptIn(ExperimentalComposeUiApi::class)
actual fun getWindowSize(): DpSize {
    val screen = LocalWindowInfo.current.containerSize
    val width = screen.width
    val height = screen.height
    // Convert points to dp
    val density = UIScreen.mainScreen.scale
    return DpSize((width / density).dp, (height / density).dp)
}
