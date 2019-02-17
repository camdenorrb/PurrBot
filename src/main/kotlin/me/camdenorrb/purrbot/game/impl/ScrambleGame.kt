package me.camdenorrb.purrbot.game.impl

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.minibus.event.EventWatcher
import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.events.scramble.ScrambleTimeoutEvent
import me.camdenorrb.purrbot.events.scramble.ScrambleWinEvent
import me.camdenorrb.purrbot.ext.findPair
import me.camdenorrb.purrbot.ext.format
import me.camdenorrb.purrbot.game.struct.Game
import me.camdenorrb.purrbot.rank.ScrambleRank
import me.camdenorrb.purrbot.store.MemberStore
import me.camdenorrb.purrbot.utils.createEmbed
import me.camdenorrb.purrbot.variables.SYMBOLS_REGEX
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color
import java.io.File

class ScrambleGame(val scramblerFolder: File, val memberStore: MemberStore) : Game(inject("mainChat")) {//, 600_000, 120_000) {

    override val name = "Scramble"


    var startTime: Long? = null
        private set

    var currentWord: String? = null
        private set


    private val words by lazy {
        File("words.txt").readLines()
    }


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
        event.game.channel.sendMessage(builder.build()).queue()
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

        val startTime = this.startTime ?: return

        val currentWord = this.currentWord ?: return


        if (event.channel.idLong != ChannelData.MAIN_CHAT_ID) {
            return
        }

        if (!event.message.contentStripped.replace(SYMBOLS_REGEX, "").equals(currentWord, true)) {
            return
        }


        val wins = memberStore.getOrMake(event.member).scramblerData.wins + 1

        val timeInSeconds = (System.currentTimeMillis() - startTime) / 1000.0

        val embed = createEmbed {
            setColor(Color.GREEN)
            setTitle("${event.author.asTag} has unscrambled the word '$currentWord' in ${timeInSeconds.format()} seconds and currently has $wins wins!")
        }

        event.channel.sendMessage(embed).queue()
        solved(event.member)
    }


    override fun onEnable() {

        val word = words.random()

        currentWord = word
        startTime = System.currentTimeMillis()

        val embed1 = createEmbed {
            setColor(Color.YELLOW)
            setTitle("The first to unscramble the word '${word.scramble()}' wins!")
        }

        channel.sendMessage(embed1).queue()


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
            channel.sendMessage(embed2).queue()
        }
    }


    fun solved(winner: Member?) {

        if (winner != null) {

            val member = memberStore.getOrMake(winner)
            member.scramblerData.wins += 1

            miniBus(ScrambleWinEvent(member, this))
        }
        else {
            miniBus(ScrambleTimeoutEvent(this))
        }

        startTime = null
        currentWord = null

        disable()
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