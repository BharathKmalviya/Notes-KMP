package app.academy.di

import app.academy.data.NotesRepository

actual class AppModule {
    /**
     * Provides an implementation of [NotesRepository]
     */
    actual fun provideNotesRepository(): NotesRepository {
        return TODO()
    }

    /**
     * Used to provide an instance of [DispatchersProviderList]
     */
    actual fun provideDispatchersProvider(): DispatchersProviderList {
        return TODO()
    }

}
