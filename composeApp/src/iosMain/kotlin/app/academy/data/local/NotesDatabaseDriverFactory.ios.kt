package app.academy.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.app.academy.notes.database.NotesDatabase

actual class NotesDatabaseDriverFactory {

    actual fun createDriver(): SqlDriver = NativeSqliteDriver(
        schema = NotesDatabase.Schema,
        name = DatabaseDriverConstants.DATABASE_NAME
    )
}
