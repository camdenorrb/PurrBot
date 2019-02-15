package me.camdenorrb.purrbot.net.response

import com.google.gson.annotations.SerializedName

data class CatResponse(@SerializedName("file") val link: String)