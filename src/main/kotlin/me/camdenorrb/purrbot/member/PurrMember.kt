package me.camdenorrb.purrbot.member

import net.dv8tion.jda.core.entities.Member
import java.io.File

class PurrMember(member: Member) : Member by member {

    private var wins: Long? = null


    val winnerFile by lazy {
        File("Winners", user.id)
    }


    fun getWins(): Long {

        if (wins == null) {
            if (!winnerFile.exists()) return 0
            else wins = winnerFile.readText().toLong()
        }

        return wins!!
    }

    fun setWins(value: Long) {
        wins = value
        winnerFile.writeText("$value")
    }

}