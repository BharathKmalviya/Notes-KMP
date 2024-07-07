package app.academy.di

import kotlinx.coroutines.CoroutineDispatcher

actual object DispatchersProvider {
    actual val io: CoroutineDispatcher
        get() = TODO("Not yet implemented")
}
