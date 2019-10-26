package io.usoamic.testcli.di

import dagger.Component
import io.usoamic.testcli.*
import javax.inject.Singleton

@Singleton
@Component(modules = [TestCliModule::class, TestCoreModule::class, TestUsoamicModule::class])
interface TestAppComponent {
    fun inject(clazz: AccountManagerTest)
    fun inject(clazz: IdeasTest)
    fun inject(clazz: NotesTest)
    fun inject(clazz: OwnerTest)
    fun inject(clazz: PurchasesTest)
    fun inject(clazz: SwapTest)
    fun inject(clazz: TransactionExplorerTest)
    fun inject(clazz: UsoamicTest)
}