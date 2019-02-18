package me.camdenorrb.purrbot.cmd.struct.context

import me.camdenorrb.kcommons.cmd.CmdContext
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.events.message.MessageReceivedEvent

data class MemberCmdContext(val member: Member, val args: List<String>, val guild: Guild, val jda: JDA, val channel: MessageChannel, val message: Message, val event: MessageReceivedEvent) : CmdContext {

    val params: List<String> by lazy {
        args.drop(1)
    }

    companion object {

        fun fromEvent(event: MessageReceivedEvent, args: List<String>): MemberCmdContext {
            return MemberCmdContext(event.member, args, event.guild, event.jda, event.channel, event.message, event)
        }

    }

}