package me.camdenorrb.purrbot.utils

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed

fun createEmbed(build: EmbedBuilder.() -> Unit): MessageEmbed {
    return EmbedBuilder().apply(build).build()
}