package me.camdenorrb.purrbot

import com.google.gson.Gson
import me.camdenorrb.kdi.KDI
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.events.Event
import net.dv8tion.jda.core.events.ReadyEvent
import net.dv8tion.jda.core.hooks.EventListener
import okhttp3.OkHttpClient
import java.io.File

object Main : EventListener {

    val gson = Gson()

    val okHttpClient = OkHttpClient()


    lateinit var jda: JDA
        private set


    @JvmStatic
    fun main(args: Array<String>) {

        val builder = JDABuilder(File("token.txt").readText()).addEventListener(this)
        jda = builder.build()

        KDI.insertAll {
            producer { jda }
            producer { gson }
            producer { okHttpClient }
        }
    }

    override fun onEvent(event: Event) {

        if (event !is ReadyEvent) return

        PurrBot().enable()

        println("\nThe bot is now ready")
        println("Invite URL: ${jda.asBot().getInviteUrl()}")
    }

}
