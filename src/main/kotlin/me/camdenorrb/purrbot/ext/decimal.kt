package me.camdenorrb.purrbot.ext

import me.camdenorrb.purrbot.variables.DECIMAL_FORMAT

fun Double.format(): String {
    return DECIMAL_FORMAT.format(this)
}