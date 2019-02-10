package me.camdenorrb.purrbot.listeners

import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.ext.format
import me.camdenorrb.purrbot.tasks.ScrambleTask
import me.camdenorrb.purrbot.utils.createEmbed
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.awt.Color
import java.io.File

class ScrambleListener(val scrambleTask: ScrambleTask) : ListenerAdapter() {

    private val winnerFolder = File("Winners").apply { mkdirs() }


    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {

        val currentWord = scrambleTask.currentWord ?: return

        val createdTime = scrambleTask.startTime ?: return


        if (event.channel.idLong != ChannelData.MAIN_CHAT_ID) {
            return
        }

        if (!event.message.contentRaw.equals(currentWord, true)) {
            return
        }


        val timeInSeconds = (System.currentTimeMillis() - createdTime) / 1000.0

        val embed = createEmbed {
            setColor(Color.GREEN)
            setTitle("${event.author.asTag} has unscrambled the word '$currentWord' in ${timeInSeconds.format()} seconds!")
        }


        scrambleTask.solved(event.member)

        val winnerFile = File(winnerFolder, event.author.id)

        if (winnerFile.createNewFile()) {
            winnerFile.writeText("1")
        }
        else {
            winnerFile.writeText("${winnerFile.readText().toInt() + 1}")
        }

        event.channel.sendMessage(embed).queue()
    }


}