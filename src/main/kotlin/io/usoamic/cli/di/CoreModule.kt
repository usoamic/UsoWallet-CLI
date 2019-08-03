package io.usoamic.cli.di

import dagger.Module
import dagger.Provides
import io.usoamic.cli.core.*
import io.usoamic.usoamickotlin.core.Usoamic
import javax.inject.Singleton

@Module
class CoreModule {
    @Provides
    @Singleton
    fun provideAccountManager(usoamic: Usoamic): AccountManager {
        return AccountManager(usoamic)
    }

    @Provides
    @Singleton
    fun provideHelp(): Help {
        return Help()
    }

    @Provides
    @Singleton
    fun provideIdeas(usoamic: Usoamic): Ideas {
        return Ideas(usoamic)
    }

    @Provides
    @Singleton
    fun provideNotes(usoamic: Usoamic): Notes {
        return Notes(usoamic)
    }

    @Provides
    @Singleton
    fun provideOwner(usoamic: Usoamic): Owner {
        return Owner(usoamic)
    }

    @Provides
    @Singleton
    fun providePurchases(usoamic: Usoamic): Purchases {
        return Purchases(usoamic)
    }

    @Provides
    @Singleton
    fun provideSwap(usoamic: Usoamic): Swap {
        return Swap(usoamic)
    }

    @Provides
    @Singleton
    fun provideTransactionExplorer(usoamic: Usoamic): TransactionExplorer {
        return TransactionExplorer(usoamic)
    }

    @Provides
    @Singleton
    fun provideUsoamic(usoamic: Usoamic): io.usoamic.cli.core.Usoamic {
        return Usoamic(usoamic)
    }
}