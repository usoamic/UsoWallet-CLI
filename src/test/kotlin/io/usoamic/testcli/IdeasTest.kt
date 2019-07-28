package io.usoamic.testcli

import io.usoamic.cli.Core
import javax.inject.Inject

class IdeasTest {
    @Inject lateinit var core: Core

    init {
        BaseUnitTest.componentTest.inject(this)
    }
}