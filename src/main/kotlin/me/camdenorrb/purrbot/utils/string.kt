package me.camdenorrb.purrbot.utils

import java.util.*


fun String?.fromBase(): String {
    return this?.let { String(Base64.getDecoder().decode(it)) } ?: ""
}