package app.academy

import android.app.Application
import app.academy.di.AppModule
import app.academy.di.getKoinModule
import org.koin.core.context.startKoin

class NotesAppApplication : Application() {
    val appModule by lazy { AppModule(this) }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(getKoinModule(appModule = appModule))
        }
    }
}
