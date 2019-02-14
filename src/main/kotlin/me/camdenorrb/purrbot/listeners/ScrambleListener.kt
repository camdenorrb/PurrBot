package me.camdenorrb.purrbot.listeners

import me.camdenorrb.minibus.event.EventWatcher
import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.events.ScrambleWinEvent
import me.camdenorrb.purrbot.ext.findPair
import me.camdenorrb.purrbot.ext.format
import me.camdenorrb.purrbot.rank.ScrambleRank
import me.camdenorrb.purrbot.store.MemberStore
import me.camdenorrb.purrbot.tasks.ScrambleTask
import me.camdenorrb.purrbot.utils.createEmbed
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.awt.Color
import java.io.File

class ScrambleListener(val scrambleTask: ScrambleTask, val memberStore: MemberStore) : ListenerAdapter() {

    private val winnerFolder = File("Winners").apply { mkdirs() }


    @EventWatcher
    fun onWin(event: ScrambleWinEvent) {

        val winner = memberStore.getOrMake(event.winner)
        val wins = winner.getWins()

        val currentRank = ScrambleRank.byWins(wins) ?: return

        val discordRank = winner.roles.findPair { role ->
            ScrambleRank.values().find { it.id == role.idLong }
        }

        if (currentRank == discordRank) return

        val guild = event.winner.guild

        if (discordRank != null) {
            guild.controller.removeRolesFromMember(winner, discordRank.first).queue()
        }

        guild.controller.addRolesToMember(winner, guild.getRoleById(currentRank.id)).queue()

        val embed = createEmbed {
            setColor(Color.CYAN.darker())
            setTitle("You have earned the '${currentRank.displayName}' role for '$wins' wins!")
        }

        val builder = MessageBuilder(embed).append(winner.asMention)
        event.scrambleTask.channel.sendMessage(builder.build()).queue()
    }

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