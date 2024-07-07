package app.academy.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import app.academy.utils.asNativeStateFlow
import cafe.adriel.voyager.core.model.ScreenModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class DemoViewModel : ScreenModel {

    private val _uiState = mutableStateListOf("testA")
    val uiState = _uiState

    fun getGreetingPrefixFromServer(): String {
        return "I am from server - Oye"
    }

    fun addToList(int: Int) {
        Logger.d("CompLifeBug") { "Random number :$int" }
        _uiState.add("Test $int")
        Logger.d("CompLifeBug") { "uistate updated called , new value $_uiState ${_uiState.size}" }

    }

    override fun onDispose() {
        super.onDispose()
        Logger.d("CompLifeBug") { "DemoVM disposed" }
    }
}
