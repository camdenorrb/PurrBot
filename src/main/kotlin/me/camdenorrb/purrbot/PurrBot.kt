package me.camdenorrb.purrbot

import me.camdenorrb.kcommons.base.ModuleStruct
import me.camdenorrb.kdi.KDI
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.purrbot.cmd.impl.CatCmd
import me.camdenorrb.purrbot.cmd.impl.LeaderBoardCmd
import me.camdenorrb.purrbot.data.ChannelData
import me.camdenorrb.purrbot.data.GuildData
import me.camdenorrb.purrbot.game.engine.GameEngine
import me.camdenorrb.purrbot.game.impl.ScrambleGame
import me.camdenorrb.purrbot.game.impl.TriviaGame
import me.camdenorrb.purrbot.listeners.ModListener
import me.camdenorrb.purrbot.store.CmdStore
import me.camdenorrb.purrbot.store.MemberStore
import me.camdenorrb.purrbot.store.ScramblerStore
import net.dv8tion.jda.core.JDA
import java.io.File

class PurrBot(val jda: JDA = inject()) : ModuleStruct() {

    override val name = "PurrBot"


    val gameManager = GameEngine(0, 600_000)

    val scramblerFolder = File("Scramblers").apply { mkdirs() }


    val cmdStore = CmdStore()

    val scramblerStore = ScramblerStore(scramblerFolder)

    val memberStore = MemberStore(mainGuild, scramblerStore)


    val modules = listOf(cmdStore, memberStore, scramblerStore)


    override fun onEnable() {

        cmdStore.register(CatCmd(), LeaderBoardCmd(scramblerStore))

        KDI.insert("mainChat") { jda.getTextChannelById(ChannelData.MAIN_CHAT_ID) }

        modules.forEach { it.enable() }

        gameManager.register(TriviaGame(), ScrambleGame(scramblerFolder, memberStore))
        gameManager.enable()

        jda.addEventListener(ModListener())
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