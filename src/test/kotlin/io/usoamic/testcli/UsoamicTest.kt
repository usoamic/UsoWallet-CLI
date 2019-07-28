package io.usoamic.testcli

import io.usoamic.cli.App
import io.usoamic.cli.Core
import org.junit.jupiter.api.Test
import javax.inject.Inject

class UsoamicTest {
    @Inject lateinit var core: Core

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun getBalanceTest() {
        val s = core.getResponse("add_idea 1234 good")
        println(s)
    }

}