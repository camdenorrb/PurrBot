package me.camdenorrb.purrbot.cmd.impl

import me.camdenorrb.purrbot.cmd.struct.MemberCmd
import me.camdenorrb.purrbot.cmd.struct.context.MemberCmdContext
import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.store.ScramblerStore
import me.camdenorrb.purrbot.utils.createEmbed
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.MessageChannel
import java.awt.Color

class LeaderBoardCmd(val scramblerStore: ScramblerStore) : MemberCmd("leaderboard") {

    override val name = "Leader Board Command"

    override val desc = "A leaderboard of the best scramble players"


    override fun MemberCmdContext.execute() {

        val embed = createEmbed {

            setColor(Color.GREEN)

            val winners = scramblerStore.data().values.sortedDescending()

            var count = 1

            addField("Unscrambler Leaders", winners.take(10).joinToString("\n") {
                "**${count++}**. ${it.displayName} | ${it.wins}"
            }, false)

        }

        reply(embed)
    }

    override fun canExecute(guild: Guild, channel: MessageChannel, member: Member): Boolean {
        return channel.idLong == ChannelData.BOT_CMDS_ID
    }

}