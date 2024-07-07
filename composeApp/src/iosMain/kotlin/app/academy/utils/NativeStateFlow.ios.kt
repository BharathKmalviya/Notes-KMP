package app.academy.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * A cross platform implementation of [StateFlow].
 */
actual class NativeStateFlow<T> actual constructor(source: StateFlow<T>) : StateFlow<T> by source {

    fun subscribe(onCollect: (T) -> Unit): DisposableHandle {
        val scope = MainScope().apply {
            launch(Dispatchers.Main) { collect(onCollect) }
        }
        return DisposableHandle { scope.cancel() }
    }
}
