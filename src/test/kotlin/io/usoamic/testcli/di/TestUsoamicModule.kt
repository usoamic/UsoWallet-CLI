package io.usoamic.testcli.di

import dagger.Module
import dagger.Provides
import io.usoamic.testcli.other.TestConfig
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.enumcls.NodeProvider
import io.usoamic.usoamickt.util.DirectoryUtils
import javax.inject.Singleton

@Module
class TestUsoamicModule {
    @Provides
    @Singleton
    fun provideContract(): Usoamic {
        return Usoamic(
            fileName = TestConfig.ACCOUNT_FILENAME,
            filePath = DirectoryUtils.getDefaultKeyDirectory(),
            networkType = NetworkType.TestNet,
            nodeProvider = NodeProvider.Infura(
                TestConfig.INFURA_PROJECT_ID
            )
        )
    }
}