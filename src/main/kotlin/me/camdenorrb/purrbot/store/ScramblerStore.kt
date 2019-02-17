package me.camdenorrb.purrbot.store

import me.camdenorrb.kcommons.store.struct.MappedStore
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.purrbot.PurrBot
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import java.io.File

class ScramblerStore(val scramblerFolder: File, val jda: JDA = inject(), val guild: Guild = PurrBot.mainGuild) : MappedStore<Long, ScramblerStore.ScramblerData>() {

    override val name = "Scrambler Store"


    override fun onEnable() {
        scramblerFolder.listFiles().forEach {
            val scramblerData = readScramblerData(it)
            data[scramblerData.id] = scramblerData
        }
    }


    operator fun get(member: Member): ScramblerData? {
        return get(member.user.idLong)
    }


    fun getOrMake(member: Member): ScramblerData {
        return this[member] ?: ScramblerData(member.user.idLong, 0, member.user.asTag).apply {
            writeTo(File(scramblerFolder, member.user.id))
        }
    }


    private fun readScramblerData(file: File): ScramblerData {
        val args = file.readLines()
        return ScramblerData(file.name.toLong(), args[0].toLong(), args[1])
    }


    inner class ScramblerData internal constructor(val id: Long, wins: Long, val tag: String): Comparable<ScramblerData> {

        val displayName get() = member?.nickname ?: name


        val scramblerFile by lazy {
            File(scramblerFolder, "$id")
        }

        val name by lazy {
            tag.substring(0, tag.indexOf('#'))
        }

        val member: Member? by lazy {
            guild.getMemberById(id)
        }


        var wins = wins
            set(value) {
                scramblerFile.writeText("$value\n$tag")
                field = value
            }


        override fun compareTo(other: ScramblerData): Int {
            return wins.compareTo(other.wins)
        }


        fun writeTo(file: File) {
            file.writeText("$wins\n$tag")
        }

    }

}