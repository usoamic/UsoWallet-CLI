package io.usoamic.testcli.di

import dagger.Module
import dagger.Provides
import io.usoamic.testcli.other.TestConfig
import io.usoamic.usoamickotlin.core.Usoamic
import io.usoamic.usoamickotlin.other.Config
import javax.inject.Singleton

@Module
class TestUsoamicModule {
    @Provides
    @Singleton
    fun provideContract(): Usoamic {
        return Usoamic(TestConfig.ACCOUNT_FILENAME, TestConfig.CONTRACT_ADDRESS, TestConfig.NODE)
    }
}