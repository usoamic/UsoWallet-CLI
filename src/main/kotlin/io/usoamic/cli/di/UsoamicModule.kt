package io.usoamic.cli.di

import dagger.Module
import dagger.Provides
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.other.Config
import javax.inject.Singleton

@Module
class UsoamicModule {
    @Provides
    @Singleton
    fun provideContract(): Usoamic {
        return Usoamic(Config.ACCOUNT_FILENAME, "0x73ece1092f843c2d16d8902c40a585139a41da25", Config.NODE)
    }
}