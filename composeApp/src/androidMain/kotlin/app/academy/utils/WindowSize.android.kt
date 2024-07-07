package app.academy.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
actual fun getWindowSize(): DpSize {
    val screen = LocalConfiguration.current
    return DpSize(screen.screenWidthDp.dp, screen.screenHeightDp.dp)
}
