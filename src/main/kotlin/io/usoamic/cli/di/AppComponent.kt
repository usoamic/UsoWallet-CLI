package io.usoamic.cli.di

import dagger.Component
import io.usoamic.cli.UsoWalletCli
import javax.inject.Singleton

@Singleton
@Component(modules = [CliModule::class, CoreModule::class, UsoamicModule::class])
interface AppComponent {
    fun inject(clazz: UsoWalletCli)
}