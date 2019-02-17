package me.camdenorrb.purrbot.cmd.struct

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.MessageChannel

abstract class StaffCmd(vararg aliases: String) : MemberCmd(*aliases) {

    override fun canExecute(guild: Guild, channel: MessageChannel, member: Member): Boolean {
        return member.roles.any { it.idLong == STAFF_ROLE_ID }
    }


    private companion object {
        const val STAFF_ROLE_ID = 528083190276030464
    }

}