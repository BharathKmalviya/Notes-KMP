package app.academy.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual object DispatchersProvider {
    actual val io: CoroutineDispatcher = Dispatchers.IO
}
