package io.usoamic.testcli

import io.usoamic.testcli.di.DaggerTestAppComponent
import io.usoamic.testcli.di.TestAppComponent
import io.usoamic.testcli.di.TestCliModule

class BaseUnitTest {
    companion object {
        val componentTest: TestAppComponent = buildDagger()
        private fun buildDagger(): TestAppComponent {
            return DaggerTestAppComponent
                .builder()
                .testCliModule(TestCliModule())
                .build()
        }
    }
}