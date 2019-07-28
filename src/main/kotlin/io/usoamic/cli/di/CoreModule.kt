package io.usoamic.cli.di

import dagger.Provides
import io.usoamic.cli.core.*
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.other.Config
import javax.inject.Singleton

class CoreModule {
    @Provides
    @Singleton
    fun provideAccountManager(usoamic: Usoamic): AccountManager {
        return AccountManager(usoamic)
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
}