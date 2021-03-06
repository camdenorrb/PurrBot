package me.camdenorrb.purrbot.listeners

/*
import me.camdenorrb.minibus.event.EventWatcher
import me.camdenorrb.minibus.listener.MiniListener
import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.events.scramble.ScrambleWinEvent
import me.camdenorrb.purrbot.ext.findPair
import me.camdenorrb.purrbot.ext.format
import me.camdenorrb.purrbot.rank.ScrambleRank
import me.camdenorrb.purrbot.store.MemberStore
import me.camdenorrb.purrbot.tasks.ScrambleTask
import me.camdenorrb.purrbot.utils.createEmbed
import me.camdenorrb.purrbot.variables.SYMBOLS_REGEX
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color
import java.io.File

class ScrambleListener(val scramblerFolder: File, val scrambleTask: ScrambleTask, val memberStore: MemberStore) : MiniListener {

    @EventWatcher
    fun onWin(event: ScrambleWinEvent) {

        val winner = event.winner
        val member = winner.member

        val wins = winner.scramblerData.wins
        val currentRank = ScrambleRank.byWins(wins) ?: return

        val discordRank = member.roles.findPair { role ->
            ScrambleRank.values().find { it.id == role.idLong }
        }

        if (currentRank == discordRank?.second) return

        val guild = member.guild

        if (discordRank != null) {
            guild.controller.removeSingleRoleFromMember(member, discordRank.first).queue()
        }

        guild.controller.addSingleRoleToMember(member, guild.getRoleById(currentRank.id)).queue()

        val embed = createEmbed {
            setColor(Color.CYAN.darker())
            setTitle("You have earned the '${currentRank.displayName}' role!")
        }

        val builder = MessageBuilder(embed).append(member.asMention)
        event.scrambleTask.channel.sendMessage(builder.build()).queue()
    }

    @EventWatcher
    fun onGuildMemberJoin(event: GuildMemberJoinEvent) {

        val winnerFile = File(scramblerFolder, event.user.id)
        if (!winnerFile.exists()) return

        val guild = event.guild
        val member = event.member

        val wins = memberStore.getOrMake(member).scramblerData.wins
        val rank = ScrambleRank.byWins(wins)?.let { guild.getRoleById(it.id) } ?: return

        guild.controller.addSingleRoleToMember(member, rank).queue()
    }

    @EventWatcher
    fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {

        val currentWord = scrambleTask.currentWord ?: return

        val createdTime = scrambleTask.startTime ?: return


        if (event.channel.idLong != ChannelData.MAIN_CHAT_ID) {
            return
        }

        if (!event.message.contentStripped.replace(SYMBOLS_REGEX, "").equals(currentWord, true)) {
            return
        }


        val wins = memberStore.getOrMake(event.member).scramblerData.wins + 1

        val timeInSeconds = (System.currentTimeMillis() - createdTime) / 1000.0

        val embed = createEmbed {
            setColor(Color.GREEN)
            setTitle("${event.author.asTag} has unscrambled the word '$currentWord' in ${timeInSeconds.format()} seconds and currently has $wins wins!")
        }

        event.channel.sendMessage(embed).queue()
        scrambleTask.solved(event.member)
    }


}*/
