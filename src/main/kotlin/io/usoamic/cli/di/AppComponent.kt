package io.usoamic.cli.di

import dagger.Component
import io.usoamic.cli.UsoWalletCli
import io.usoamic.cli.core.*
import javax.inject.Singleton

@Singleton
@Component(modules = [UsoamicModule::class])
interface AppComponent {
    fun inject(clazz: UsoWalletCli)
    fun inject(clazz: Ideas)
    fun inject(clazz: Notes)
    fun inject(clazz: Owner)
    fun inject(clazz: Purchases)
    fun inject(clazz: Swap)
    fun inject(clazz: TransactionExplorer)
    fun inject(clazz: Usoamic)
    fun inject(clazz: AccountManager)
}