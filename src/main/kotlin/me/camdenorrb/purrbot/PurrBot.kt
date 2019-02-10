package me.camdenorrb.purrbot

import me.camdenorrb.kdi.KDI
import me.camdenorrb.minibus.MiniBus
import me.camdenorrb.purrbot.listeners.ModListener
import me.camdenorrb.purrbot.listeners.ScrambleListener
import me.camdenorrb.purrbot.struct.Module
import me.camdenorrb.purrbot.tasks.ScrambleTask
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.events.Event
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.hooks.EventListener

class PurrBot(token: String) : Module(), EventListener {

    val miniBus = MiniBus()

    val builder = JDABuilder(token).addEventListener(this, ModListener())


    lateinit var bot: JDA
        private set


    override fun onEnable() {
        KDI.insert { miniBus }
        bot = builder.build()
    }


    override fun onEvent(event: Event) {

        if (event !is ReadyEvent) return

        bot.addEventListener(ScrambleListener(ScrambleTask(bot).apply { enable() }))

        println("\nThe bot is now ready")
        println("Invite URL: ${bot.asBot().getInviteUrl()}")
    }

}