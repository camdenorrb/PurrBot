package me.camdenorrb.purrbot.ext

import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.utils.createEmbed
import net.dv8tion.jda.core.entities.Message
import java.awt.Color

fun Message.deleteAndLog() {

    delete().queue({}, {})

    val author = author

    val embed = createEmbed {

        setColor(Color.ORANGE.darker())
        setTitle("Deleted a message in #${textChannel.name}")

        addField("Sender", "${author.asTag} (${author.id})", false)

        if (contentRaw.length <= 1022) {
            addField("Message", "`$contentRaw`", false)
        }
        else {
            contentRaw.chunked(1022).forEachIndexed { index, it ->
                addField("Message (Part ${index + 1})", "`$it`", false)
            }
        }
    }

    guild.getTextChannelById(ChannelData.PURR_LOGGING_ID).sendMessage(embed).queue()
}