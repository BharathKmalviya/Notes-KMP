package app.academy.di

import org.koin.dsl.module

fun getKoinModule(appModule: AppModule) = module {
    single { appModule.provideNotesRepository() }
    single { appModule.provideDispatchersProvider() }
}
