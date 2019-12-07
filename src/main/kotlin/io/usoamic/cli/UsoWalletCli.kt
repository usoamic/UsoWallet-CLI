package io.usoamic.cli

import io.usoamic.cli.core.Core
import io.usoamic.cli.exception.CommandNotFoundException
import io.usoamic.cli.exception.ContractNullPointerException
import io.usoamic.cli.exception.ObjectNotFoundException
import io.usoamic.usoamickt.exception.InvalidMnemonicPhraseException
import io.usoamic.usoamickt.exception.InvalidPrivateKeyException
import java.util.*
import javax.inject.Inject

class UsoWalletCli {
    @Inject lateinit var core: Core

    init {
        App.component.inject(this)
        val input = Scanner(System.`in`)
        print("> ")
        while (input.hasNextLine()) {
            try {
                val line = input.nextLine()
                if(line.isNotEmpty()) {
                    println(core.getResponse(line))
                }
            }
            catch (e: Exception) {
                when (e) {
                    is InvalidMnemonicPhraseException -> {
                        println("Invalid Mnemonic Phrase")
                    }
                    is InvalidPrivateKeyException -> {
                        println("Invalid Private Key")
                    }
                    is ObjectNotFoundException -> {
                        println("Not found")
                    }
                    is ContractNullPointerException -> {
                        println("Response is null")
                    }
                    is CommandNotFoundException -> {
                        println("Command Not Found")
                    }
                    else -> {
                        println("Error: ${e.message}")
                    }
                }
            }
            print("> ")
        }
    }


}