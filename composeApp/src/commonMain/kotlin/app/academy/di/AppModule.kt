package app.academy.di

import app.academy.data.NotesRepository

expect class AppModule {
    /**
     * Provides an implementation of [NotesRepository]
     */
    fun provideNotesRepository(): NotesRepository

    /**
     * Used to provide an instance of [DispatchersProviderList]
     */
    fun provideDispatchersProvider(): DispatchersProviderList

}
