package io.usoamic.testcli.di

import dagger.Module
import dagger.Provides
import io.usoamic.cli.core.Core
import io.usoamic.cli.core.*
import io.usoamic.cli.core.Usoamic
import javax.inject.Singleton

@Module
class TestCliModule {
    @Provides
    @Singleton
    fun provideCore(accountManager: AccountManager,
                    ideas: Ideas,
                    notes: Notes,
                    owner: Owner,
                    purchases: Purchases,
                    swap: Swap,
                    transactionExplorer: TransactionExplorer,
                    usoamic: Usoamic): Core {
        return Core(
            accountManager,
            ideas,
            notes,
            owner,
            purchases,
            swap,
            transactionExplorer,
            usoamic
        )
    }
}