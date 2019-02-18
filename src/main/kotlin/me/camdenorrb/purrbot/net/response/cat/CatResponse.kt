package me.camdenorrb.purrbot.net.response.cat

import com.google.gson.annotations.SerializedName

data class CatResponse(@SerializedName("file") val link: String)