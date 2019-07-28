package io.usoamic.testcli

import io.usoamic.cli.Core
import io.usoamic.usoamickotlin.core.Usoamic
import javax.inject.Inject

class IdeasTest {
    @Inject lateinit var core: Core
    @Inject lateinit var usoamic: Usoamic

    init {
        BaseUnitTest.componentTest.inject(this)
    }
}