package io.usoamic.testcli.di

import dagger.Module
import dagger.Provides
import io.usoamic.testcli.other.TestConfig
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enum.NetworkType
import io.usoamic.usoamickt.enum.NodeProvider
import javax.inject.Singleton

@Module
class TestUsoamicModule {
    @Provides
    @Singleton
    fun provideContract(): Usoamic {
        return Usoamic(TestConfig.ACCOUNT_FILENAME, NetworkType.TESTNET, NodeProvider.INFURA)
    }
}