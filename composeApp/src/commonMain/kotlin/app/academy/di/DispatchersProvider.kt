package app.academy.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

expect object DispatchersProvider {
    val io: CoroutineDispatcher
}

/**
 * A class that contains all required [CoroutineDispatcher]'s.
 */
data class DispatchersProviderList(
    val ioDispatcher: CoroutineDispatcher = DispatchersProvider.io,
    val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
)
