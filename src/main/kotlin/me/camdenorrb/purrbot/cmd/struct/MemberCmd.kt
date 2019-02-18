package me.camdenorrb.purrbot.cmd.struct

import me.camdenorrb.kcommons.base.Named
import me.camdenorrb.kcommons.cmd.Cmd
import me.camdenorrb.purrbot.cmd.struct.context.MemberCmdContext
import me.camdenorrb.purrbot.utils.createEmbed
import net.dv8tion.jda.core.entities.*
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException

abstract class MemberCmd(vararg aliases: String) : Cmd<MemberCmdContext>, Named {

    open val minArgs: Int = 0

    open val usage: String? = null

    abstract val desc: String

    val aliases = aliases.map { it.toLowerCase() }


    override fun MemberCmdContext.onCantExecute() = kotlin.Unit

    override fun isThis(cmd: String): Boolean {
        return aliases.any { it.equals(cmd, true) }
    }

    final override fun MemberCmdContext.canExecute(): Boolean {
        return canExecute(guild, channel, member)
    }

    open fun canExecute(guild: Guild, channel: MessageChannel, member: Member): Boolean {
        return true
    }


    protected open fun MemberCmdContext.reply(msg: Any, success: (Message) -> Unit = {}, failure: (Throwable) -> Unit = {}) {
        reply(msg.toString(), success, failure)
    }

    protected open fun MemberCmdContext.reply(msg: String, success: (Message) -> Unit = {}, failure: (Throwable) -> Unit = {}) {
        try {
            channel.sendMessage(msg).queue(success, failure)
        }
        catch (ignored: InsufficientPermissionException) {}
    }

    protected open fun MemberCmdContext.reply(msg: Message, success: (Message) -> Unit = {}, failure: (Throwable) -> Unit = {}) {
        try {
            channel.sendMessage(msg).queue(success, failure)
        }
        catch (ignored: InsufficientPermissionException) {}
    }

    protected open fun MemberCmdContext.reply(msg: MessageEmbed, success: (Message) -> Unit = {}, failure: (Throwable) -> Unit = {}) {
        try {
            channel.sendMessage(msg).queue(success, failure)
        }
        catch (ex: InsufficientPermissionException) {
            reply("Please give me permissions to send embed messages")
        }
    }

    protected open fun MemberCmdContext.replyDirect(msg: Any, success: (Message) -> Unit = {}, failure: (Throwable) -> Unit = {}) {
        replyDirect(msg.toString(), success, failure)
    }

    protected open fun MemberCmdContext.replyDirect(msg: String, success: (Message) -> Unit = {}, failure: (Throwable) -> Unit = {}) {
        member.user.openPrivateChannel().queue {
            it.sendMessage(msg).queue(success, failure)
        }
    }

    protected open fun MemberCmdContext.replyDirect(msg: Message, success: (Message) -> Unit = {}, failure: (Throwable) -> Unit = {}) {
        member.user.openPrivateChannel().queue {
            it.sendMessage(msg).queue(success, failure)
        }
    }

    protected open fun MemberCmdContext.replyDirect(msg: MessageEmbed, success: (Message) -> Unit = {}, failure: (Throwable) -> Unit = {}) {
        member.user.openPrivateChannel().queue {
            it.sendMessage(msg).queue(success, failure)
        }
    }


    open fun MemberCmdContext.sendUsage() {

        val message = createEmbed {

            setAuthor("PurrBot - Help")
            setTitle(name)
            setColor(0xFF1493) // Hot Pink
            setDescription(desc)

            if (usage != null) {
                addField("Usage", "$$usage", false)
            }
        }

        reply(message)
    }


}