package me.camdenorrb.purrbot.utils

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.MessageEmbed

fun createEmbed(build: EmbedBuilder.() -> Unit): MessageEmbed {
    return EmbedBuilder().apply(build).build()
}