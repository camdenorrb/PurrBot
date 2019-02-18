package me.camdenorrb.purrbot

import com.google.gson.Gson
import me.camdenorrb.kdi.KDI
import me.camdenorrb.minibus.MiniBus
import me.camdenorrb.minibus.event.EventWatcher
import me.camdenorrb.minibus.listener.MiniListener
import me.camdenorrb.purrbot.impl.MiniEventManager
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.events.ReadyEvent
import okhttp3.OkHttpClient
import java.io.File

object Main : MiniListener {

    val gson = Gson()

    val miniBus = MiniBus()

    val okHttpClient = OkHttpClient()


    lateinit var jda: JDA
        private set


    @JvmStatic
    fun main(args: Array<String>) {

        jda = JDABuilder(File("token.txt").readText())
            .addEventListener(this)
            .setEventManager(MiniEventManager(miniBus))
            .build()

        KDI.insertAll {
            producer { jda }
            producer { gson }
            producer { miniBus }
            producer { okHttpClient }
        }
    }

    @EventWatcher
    private fun onReady(event: ReadyEvent) {

        PurrBot().enable()

        println("\nThe bot is now ready")
        println("Invite URL: ${jda.asBot().getInviteUrl()}")
    }

}