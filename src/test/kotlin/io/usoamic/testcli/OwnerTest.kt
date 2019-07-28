package io.usoamic.testcli

import io.usoamic.cli.Core
import javax.inject.Inject

class OwnerTest {
    @Inject lateinit var core: Core

    init {
        BaseUnitTest.componentTest.inject(this)
    }
}