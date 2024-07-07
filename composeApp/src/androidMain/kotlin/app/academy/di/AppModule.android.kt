package app.academy.di

import android.content.Context
import app.academy.data.DefaultNotesRepository
import app.academy.data.NotesRepository
import app.academy.data.local.NotesDatabaseDriverFactory
import app.academy.data.local.datasource.DefaultLocalNotesDataSource
import com.app.academy.notes.database.NotesDatabase
import kotlinx.coroutines.Dispatchers

actual class AppModule(context: Context) {

    private val database by lazy {
        val driver = NotesDatabaseDriverFactory(context).createDriver()
        NotesDatabase(driver)
    }

    /**
     * Provides an implementation of [NotesRepository]
     */
    actual fun provideNotesRepository(): NotesRepository {
        val localNotesDataSource = DefaultLocalNotesDataSource(
            database = database,
            ioDispatcher = Dispatchers.IO
        )
        return DefaultNotesRepository(localNotesDataSource = localNotesDataSource)
    }


    /**
     * Used to provide an instance of [DispatchersProvider]
     */
    actual fun provideDispatchersProvider(): DispatchersProviderList {
        return DispatchersProviderList(
            ioDispatcher = Dispatchers.IO,
            defaultDispatcher = Dispatchers.Default,
            mainDispatcher = Dispatchers.Main
        )
    }

}
