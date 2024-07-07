package app.academy.utils

import kotlinx.coroutines.flow.StateFlow

/**
 * A cross platform implementation of [StateFlow].
 */
actual class NativeStateFlow<T> actual constructor(source: StateFlow<T>) : StateFlow<T> by source
