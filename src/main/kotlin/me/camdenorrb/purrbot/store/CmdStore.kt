package me.camdenorrb.purrbot.store

import me.camdenorrb.kcommons.store.struct.MappedStore
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.minibus.event.EventWatcher
import me.camdenorrb.minibus.listener.MiniListener
import me.camdenorrb.purrbot.cmd.struct.MemberCmd
import me.camdenorrb.purrbot.cmd.struct.context.MemberCmdContext
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class CmdStore(val jda: JDA = inject()) : MappedStore<String, MemberCmd>(), MiniListener {

    override val name = "Command Store"


    override fun onEnable() {
        jda.addEventListener(this)
    }

    override fun onDisable() {
        jda.removeEventListener(this)
        super.onDisable()
    }


    fun register(vararg memberCmds: MemberCmd) {
        memberCmds.forEach { cmd ->
            cmd.aliases.forEach { register(it, cmd) }
        }
    }

    fun remove(vararg memberCmds: MemberCmd) {
        memberCmds.forEach { cmd ->
            cmd.aliases.forEach { remove(it) }
        }
    }


    @EventWatcher
    fun onMessageReceived(event: MessageReceivedEvent) {

        val content = event.message.contentStripped
        if (!content.startsWith("$")) return

        val args = content.substring(1).split(' ')
        if (args.isEmpty()) return

        val cmd = this[args[0].toLowerCase()] ?: return

        if (!cmd.canExecute(event.guild, event.channel, event.member)) return

        with(cmd) {

            val context = MemberCmdContext.fromEvent(event, args)
            if (args.size - 1 < cmd.minArgs) return context.sendUsage()

            context.execute()
        }
    }

}