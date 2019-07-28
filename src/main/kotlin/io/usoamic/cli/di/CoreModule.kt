package io.usoamic.cli.di

import dagger.Provides
import io.usoamic.cli.core.AccountManager
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.other.Config
import javax.inject.Singleton

class CoreModule {
    @Provides
    @Singleton
    fun provideAccountManager(): AccountManager {
        return AccountManager()
    }
}