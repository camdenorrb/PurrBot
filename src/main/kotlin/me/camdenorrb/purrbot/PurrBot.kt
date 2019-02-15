package me.camdenorrb.purrbot

import me.camdenorrb.kdi.KDI
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.minibus.MiniBus
import me.camdenorrb.purrbot.cmd.impl.CatCmd
import me.camdenorrb.purrbot.cmd.impl.LeaderBoardCmd
import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.data.GuildData
import me.camdenorrb.purrbot.listeners.ModListener
import me.camdenorrb.purrbot.listeners.ScrambleListener
import me.camdenorrb.purrbot.store.CmdStore
import me.camdenorrb.purrbot.store.MemberStore
import me.camdenorrb.purrbot.store.ScramblerStore
import me.camdenorrb.purrbot.struct.Module
import me.camdenorrb.purrbot.tasks.ScrambleTask
import net.dv8tion.jda.core.JDA
import java.io.File

class PurrBot(val jda: JDA = inject()) : Module() {

    val miniBus = MiniBus()

    val cmdStore = CmdStore()

    val scramblerStore = ScramblerStore(File("Scramblers").apply { mkdirs() })

    val memberStore = MemberStore(mainGuild, scramblerStore)


    val modules = listOf(cmdStore, memberStore, scramblerStore)


    override fun onEnable() {

        modules.forEach { it.enable() }

        KDI.insert("mainChat") { jda.getTextChannelById(ChannelData.MAIN_CHAT_ID) }

        val scrambleTask = ScrambleTask(memberStore, miniBus).apply { enable() }
        val scrambleListener = ScrambleListener(scrambleTask, memberStore)

        miniBus.register(scrambleListener)
        jda.addEventListener(ModListener(), scrambleListener)

        cmdStore.register(CatCmd(), LeaderBoardCmd(scramblerStore))
    }

    override fun onDisable() {
        modules.forEach { it.disable() }
    }


    companion object {

        // TODO: Make a map of stores so I can use multiple guilds
        val mainGuild by lazy {
            inject<JDA>().getGuildById(GuildData.PURR_LOUNGE_ID)
        }

    }

}