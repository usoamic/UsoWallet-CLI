package io.usoamic.cli.di

import dagger.Module
import dagger.Provides
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enum.NetworkType
import io.usoamic.usoamickt.enum.NodeProvider
import io.usoamic.usoamickt.model.Account
import javax.inject.Singleton

@Module
class UsoamicModule {
    @Provides
    @Singleton
    fun provideContract(): Usoamic {
        return Usoamic(Account.FILENAME, NetworkType.MAINNET, NodeProvider.INFURA)
    }
}