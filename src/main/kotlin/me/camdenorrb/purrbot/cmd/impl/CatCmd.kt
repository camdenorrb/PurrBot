package me.camdenorrb.purrbot.cmd.impl

import com.google.gson.Gson
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.purrbot.cmd.struct.MemberCmd
import me.camdenorrb.purrbot.cmd.struct.context.MemberCmdContext
import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.data.WebsiteData
import me.camdenorrb.purrbot.net.response.CatResponse
import me.camdenorrb.purrbot.utils.createEmbed
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.MessageChannel
import okhttp3.OkHttpClient
import okhttp3.Request
import java.awt.Color

class CatCmd(val gson: Gson = inject(), val client: OkHttpClient = inject()) : MemberCmd("cat", "kitty") {

    override val name = "Cat Command"

    override val desc = "Displays an amazing catto"


    override fun MemberCmdContext.execute() {

        val request = Request.Builder().url(WebsiteData.CAT_LINK).build()
        val response = client.newCall(request).execute()

        val responseData = response.body()!!.string()

        response.close()

        val catResponse = gson.fromJson(responseData, CatResponse::class.java)

        val embed = createEmbed {
            setColor(Color.GREEN.darker())
            setImage(catResponse.link)
        }

        reply(embed)
    }

    override fun canExecute(guild: Guild, channel: MessageChannel, member: Member): Boolean {
        return channel.idLong == ChannelData.CATS_ID
    }

}