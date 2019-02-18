package me.camdenorrb.purrbot.variables

import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.text.DecimalFormat
import java.util.*


val GSON = Gson()

val TIMER = Timer()

val OKHTTP_CLIENT = OkHttpClient()

val SYMBOLS_REGEX = Regex("[^A-Za-z0-9]")

val DECIMAL_FORMAT = DecimalFormat("#.##")