package io.usoamic.testcli

import io.usoamic.cli.Core
import io.usoamic.testcli.other.TestConfig
import io.usoamic.testcli.util.Utils
import io.usoamic.testcli.util.Utils.Companion.getNewAddress
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.web3j.exceptions.MessageDecodingException
import javax.inject.Inject

class OwnerTest {
    @Inject lateinit var core: Core

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun setFrozenTest() {
        assertThrows<MessageDecodingException> {
            core.getResponse("set_frozen ${TestConfig.PASSWORD} true")
        }
    }

    @Test
    fun setOwnerTest() {
        assertThrows<MessageDecodingException> {
            val address = getNewAddress()
            core.getResponse("set_owner ${TestConfig.PASSWORD} $address")
        }
    }
}