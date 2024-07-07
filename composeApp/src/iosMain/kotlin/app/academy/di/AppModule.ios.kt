package app.academy.di

import app.academy.data.DefaultNotesRepository
import app.academy.data.NotesRepository
import app.academy.data.local.NotesDatabaseDriverFactory
import app.academy.data.local.datasource.DefaultLocalNotesDataSource
import com.app.academy.notes.database.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual class AppModule {

    private val database by lazy {
        val driver = NotesDatabaseDriverFactory().createDriver()
        NotesDatabase(driver)
    }

    actual fun provideNotesRepository(): NotesRepository {
        val localNotesDataSource = DefaultLocalNotesDataSource(
            database = database,
            ioDispatcher = Dispatchers.IO
        )
        return DefaultNotesRepository(localNotesDataSource = localNotesDataSource)
    }

    actual fun provideDispatchersProvider(): DispatchersProviderList = DispatchersProviderList(
        ioDispatcher = Dispatchers.Default,
        defaultDispatcher = Dispatchers.Default,
        mainDispatcher = Dispatchers.Main
    )
}
