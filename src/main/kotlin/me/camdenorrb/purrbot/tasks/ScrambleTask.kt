package me.camdenorrb.purrbot.tasks

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.minibus.MiniBus
import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.events.ScrambleTimeoutEvent
import me.camdenorrb.purrbot.events.ScrambleWinEvent
import me.camdenorrb.purrbot.struct.Module
import me.camdenorrb.purrbot.utils.createEmbed
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.entities.Member
import java.awt.Color
import java.io.File

class ScrambleTask(val client: JDA, val miniBus: MiniBus = inject()) : Module() {

    private lateinit var timer: Job

    var startTime: Long? = null
        private set

    var currentWord: String? = null
        private set


    private val words by lazy {
        File("words.txt").readLines()
    }

    private val mainChatChannel by lazy {
        client.getTextChannelById(ChannelData.MAIN_CHAT_ID)
    }


    override fun onEnable() {

        timer = GlobalScope.launch {
            while (true) {
                delay(900_000)
                onTick()
            }
        }

    }

    override fun onDisable() {
        timer.cancel()
    }


    fun solved(winner: Member?) {

        if (winner != null) {
            miniBus(ScrambleWinEvent(winner, this))
        }
        else {
            miniBus(ScrambleTimeoutEvent(this))
        }

        startTime = null
        currentWord = null
    }


    private suspend fun onTick() {

        val word = words.random()

        currentWord = word
        startTime = System.currentTimeMillis()

        val embed1 = createEmbed {
            setColor(Color.YELLOW)
            setTitle("The first to unscramble the word '${word.scramble()}' wins!")
        }

        mainChatChannel.sendMessage(embed1).queue()


        GlobalScope.launch {

            delay(300_000)

            if (startTime == null || currentWord == null || currentWord != word) {
                return@launch
            }

            val embed2 = createEmbed {
                setColor(Color.RED)
                setTitle("Y'all have failed to unscramble the word '$word' :C")
            }

            solved(null)
            mainChatChannel.sendMessage(embed2).queue()
        }

    }

    private fun String.scramble(): String {

        val chars = this.toList()

        var shuffled = chars.shuffled()


        while (shuffled == chars) {
            shuffled = chars.shuffled()
        }


        return shuffled.joinToString("")
    }

}