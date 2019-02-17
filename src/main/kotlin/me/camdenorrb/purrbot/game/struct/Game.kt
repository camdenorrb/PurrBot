package me.camdenorrb.purrbot.game.struct

import me.camdenorrb.kcommons.base.ModuleStruct
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.minibus.MiniBus
import me.camdenorrb.minibus.listener.MiniListener
import me.camdenorrb.purrbot.base.Named
import me.camdenorrb.purrbot.events.game.GameEndEvent
import me.camdenorrb.purrbot.events.game.GameStartEvent
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.TextChannel

abstract class Game(val channel: TextChannel, val jda: JDA = inject(), val miniBus: MiniBus = inject()) : ModuleStruct(), Named, MiniListener {

    override fun onEnable() {
        miniBus(GameStartEvent(this))
        jda.addEventListener(this)
    }

    override fun onDisable() {
        miniBus(GameEndEvent(this))
        jda.removeEventListener(this)
    }

}