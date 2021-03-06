package io.usoamic.cli.di

import dagger.Module
import dagger.Provides
import io.usoamic.usoamickt.core.Usoamic
import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.enumcls.NodeProvider
import io.usoamic.usoamickt.model.Account
import io.usoamic.usoamickt.util.DirectoryUtils
import javax.inject.Singleton

@Module
class UsoamicModule {
    @Provides
    @Singleton
    fun provideContract(): Usoamic {
        return Usoamic(
            fileName = Account.FILENAME,
            filePath = DirectoryUtils.getDefaultKeyDirectory(),
            networkType = NetworkType.TestNet,
            nodeProvider = NodeProvider.Infura(
                "d0b30b18d6334906bcbf7d30e3dfa6bb"
            )
        )
    }
}