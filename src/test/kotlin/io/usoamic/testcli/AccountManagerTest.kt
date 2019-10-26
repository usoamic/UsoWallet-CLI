package io.usoamic.testcli

import io.usoamic.cli.core.Core
import io.usoamic.cli.exception.ValidateUtilException
import io.usoamic.testcli.other.TestConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.web3j.crypto.MnemonicUtils
import org.web3j.crypto.WalletUtils
import javax.inject.Inject

class AccountManagerTest {
    @Inject
    lateinit var core: Core

    init {
        BaseUnitTest.componentTest.inject(this)
    }

    @Test
    fun importMnemonicPhraseTest() {
        val mnemonicPhrase = "denial wrist culture into guess parade lesson black member shove wisdom strike"
        core.getResponse("import_mnemonic_phrase ${TestConfig.PASSWORD} '$mnemonicPhrase'")
    }

    @Test
    fun accountTestWhenMnemonicPhraseIsEmpty() {
        val mnemonicPhrase = ""
        assertThrows<ValidateUtilException> {
            core.getResponse("import_mnemonic_phrase ${TestConfig.PASSWORD} '$mnemonicPhrase'")
        }
    }

    @Test
    fun accountTestWhenMnemonicPhraseIsInvalid() {
        val mnemonicPhrase = "culture into"
        assertThrows<ValidateUtilException> {
            core.getResponse("import_mnemonic_phrase ${TestConfig.PASSWORD} '$mnemonicPhrase'")
        }
    }

    @Test
    fun importPrivateKeyTest() {
        val privateKey = "0x5baac668d2baaf61478294b44a83955779cace044fe8c777c6d6f122deebb8a0"
        assert(WalletUtils.isValidPrivateKey(privateKey))
        core.getResponse("import_private_key ${TestConfig.PASSWORD} $privateKey")
    }

    @Test
    fun accountTestWhenPrivateKeyIsEmpty() {
        val privateKey = ""
        assertThrows<ValidateUtilException> {
            core.getResponse("import_private_key ${TestConfig.PASSWORD} '$privateKey'")
        }
    }

    @Test
    fun accountTestWhenPrivateKeyIsInvalid() {
        val privateKey = "0x0as"
        assertThrows<ValidateUtilException> {
            core.getResponse("import_private_key ${TestConfig.PASSWORD} '$privateKey'")
        }
    }

    @Test
    fun createPrivateKeyTest() {
        val privateKey = core.getResponse("create_private_key")
        assert(WalletUtils.isValidPrivateKey(privateKey))
    }

    @Test
    fun createMnemonicPhraseTest() {
        val mnemonicPhrase = core.getResponse("create_mnemonic_phrase")
        assert(MnemonicUtils.validateMnemonic(mnemonicPhrase))
    }
}