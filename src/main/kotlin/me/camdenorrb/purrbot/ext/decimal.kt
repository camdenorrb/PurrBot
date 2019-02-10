package me.camdenorrb.purrbot.ext

import me.camdenorrb.purrbot.`var`.DECIMAL_FORMAT


fun Double.format(): String {
    return DECIMAL_FORMAT.format(this)
}