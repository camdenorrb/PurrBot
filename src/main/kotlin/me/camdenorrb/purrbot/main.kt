package me.camdenorrb.purrbot

import java.io.File

fun main() {

    val bot = PurrBot(File("token.txt").readText())
    bot.enable()

}