package me.camdenorrb.purrbot.listeners

import me.camdenorrb.minibus.event.EventWatcher
import me.camdenorrb.minibus.listener.MiniListener
import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.ext.deleteAndLog
import me.camdenorrb.purrbot.impl.MessageInfo
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class ModListener : MiniListener {

    lateinit var introHistory: MutableList<MessageInfo>


    @EventWatcher
    private fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {

        when(event.channel.idLong) {
            ChannelData.INTRO_ID -> onIntro(event)
            ChannelData.SNAPCODE_ID -> onSnapCode(event)
            ChannelData.MAIN_CHAT_ID -> onMain(event)
            ChannelData.COUNT_TO_3K_ID -> onCount(event)
            ChannelData.LOGO_CONTEST_ID -> onLogoContest(event)
            ChannelData.ONE_WORD_STORY_ID -> onOneWordStory(event)
        }

    }

    @EventWatcher
    private fun onGuildMessageDelete(event: GuildMessageDeleteEvent) {

        if (event.channel.idLong != ChannelData.INTRO_ID) return


        val messageID = event.messageIdLong

        if (::introHistory.isInitialized) {
            introHistory.removeIf { it.messageID == messageID }
        }

    }


    // Text channel handlers

    private fun onMain(event: GuildMessageReceivedEvent) {

    }

    private fun onIntro(event: GuildMessageReceivedEvent) {

        val message = event.message
        val content = message.contentRaw

        if (!content.contains("name:", true)) {
            return message.deleteAndLog()
        }

        val isInitialized = ::introHistory.isInitialized

        if (!isInitialized) {
            introHistory = event.channel.iterableHistory.map { MessageInfo(it) }.toMutableList()
        }

        val authorID = message.author.idLong

        if (introHistory.any { it.authorID == authorID }) {
            return message.deleteAndLog()
        }
        else if (!isInitialized) {
            introHistory.add(MessageInfo(event.message))
        }

    }

    fun onLogoContest(event: GuildMessageReceivedEvent) {

        val message = event.message

        if (message.attachments.isEmpty()) {
            message.deleteAndLog()
        }
    }

    fun onCount(event: GuildMessageReceivedEvent) {

        val channel = event.channel
        val message = event.message


        val number = message.contentRaw.toLongOrNull()
            ?: return message.deleteAndLog()

        val lastNumber = channel.getHistoryBefore(message.id, 1).complete()
            .retrievedHistory.firstOrNull()?.contentRaw?.toLongOrNull() ?: return


        if (number != lastNumber + 1) {
            message.deleteAndLog()
        }

    }

    private fun onSnapCode(event: GuildMessageReceivedEvent) {

        val message = event.message

        if (message.attachments.isEmpty()) {
            message.deleteAndLog()
        }
    }

    private fun onOneWordStory(event: GuildMessageReceivedEvent) {

        val message = event.message

        if (message.contentRaw.contains(' ')) {
            message.deleteAndLog()
        }
    }

}