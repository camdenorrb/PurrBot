package me.camdenorrb.purrbot.game.struct

import me.camdenorrb.kdi.ext.inject
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.entities.TextChannel
import java.util.*
import kotlin.concurrent.timer

abstract class TickedGame(channel: TextChannel, private val period: Long, private val initialDelay: Long = period, jda: JDA = inject()) : Game(channel, jda) {

    lateinit var timer: Timer
        private set


    protected open fun TimerTask.onTick() = Unit


    override fun onEnable() {
        startTimer(period, initialDelay)
        super.onEnable()
    }

    override fun onDisable() {
        stopTimer()
        super.onDisable()
    }


    protected fun startTimer(period: Long = 0, initialDelay: Long = 0, name: String = this.name): Timer {
        timer = timer(name, false, initialDelay, period) { onTick() }
        return timer
    }

    protected fun stopTimer() {
        timer.cancel()
    }

}