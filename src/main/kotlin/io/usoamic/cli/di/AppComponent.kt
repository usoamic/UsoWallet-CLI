package io.usoamic.cli.di

import dagger.Component
import io.usoamic.cli.Core
import io.usoamic.cli.UsoWalletCli
import io.usoamic.cli.core.*
import javax.inject.Singleton

@Singleton
@Component(modules = [UsoamicModule::class])
interface AppComponent {
    fun inject(clazz: UsoWalletCli)
}