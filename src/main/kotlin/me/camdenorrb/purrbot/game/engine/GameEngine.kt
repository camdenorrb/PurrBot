package me.camdenorrb.purrbot.game.engine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.camdenorrb.kcommons.store.struct.ListStore
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.minibus.MiniBus
import me.camdenorrb.minibus.event.EventWatcher
import me.camdenorrb.minibus.listener.MiniListener
import me.camdenorrb.purrbot.events.game.GameEndEvent
import me.camdenorrb.purrbot.game.struct.Game

open class GameEngine(val initStartDelay: Long, val startGameDelay: Long = initStartDelay, val miniBus: MiniBus = inject()) : ListStore<Game>(), MiniListener {

    override val name = "Game Manager"


    lateinit var currentGame: Game
        private set


    override fun onEnable() {

        GlobalScope.launch {

            if (!isEnabled) return@launch

            delay(initStartDelay)

            miniBus.register(this@GameEngine)
            currentGame = startNextGame()
        }
    }

    override fun onDisable() {
        miniBus.unregister(this)
    }


    @EventWatcher
    private fun onGameEnd(event: GameEndEvent) {

        if (currentGame != event.game) return

        GlobalScope.launch {
            delay(startGameDelay)
            currentGame = startNextGame()
        }
    }


    protected open fun pickNextGame(): Game {
        return data.random()
    }

    open fun startNextGame(): Game {

        check(isEnabled)

        if (::currentGame.isInitialized && !currentGame.isEnabled) currentGame.disable()
        return pickNextGame().apply { enable() }
    }

}