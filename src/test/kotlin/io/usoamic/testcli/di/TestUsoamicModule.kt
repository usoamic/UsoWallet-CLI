package io.usoamic.testcli.di

import dagger.Module
import dagger.Provides
import io.usoamic.testcli.other.TestConfig
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.enumcls.NodeProvider
import javax.inject.Singleton

@Module
class TestUsoamicModule {
    @Provides
    @Singleton
    fun provideContract(): Usoamic {
        return Usoamic(TestConfig.ACCOUNT_FILENAME, NetworkType.TESTNET, NodeProvider.Infura(TestConfig.INFURA_PROJECT_ID))
    }
}