package io.usoamic.cli.di

import dagger.Module
import dagger.Provides
import io.usoamic.cli.core.*
import javax.inject.Singleton

@Module
class CliModule {
    @Provides
    @Singleton
    fun provideCore(accountManager: AccountManager,
                    help: Help,
                    ideas: Ideas,
                    notes: Notes,
                    owner: Owner,
                    purchases: Purchases,
                    swap: Swap,
                    transactionExplorer: TransactionExplorer,
                    usoamic: Usoamic): Core {
        return Core(
            accountManager,
            help,
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