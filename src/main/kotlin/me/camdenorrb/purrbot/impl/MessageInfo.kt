package me.camdenorrb.purrbot.impl

import net.dv8tion.jda.core.entities.Message

data class MessageInfo(val authorID: Long, val messageID: Long, val contentRaw: String) {

    constructor(message: Message) : this(message.author.idLong, message.idLong, message.contentRaw)

}