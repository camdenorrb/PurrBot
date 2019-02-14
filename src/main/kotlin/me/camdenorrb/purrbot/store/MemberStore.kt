package me.camdenorrb.purrbot.store

import me.camdenorrb.kcommons.store.struct.MappedStore
import me.camdenorrb.purrbot.member.PurrMember
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member

class MemberStore(val guild: Guild) : MappedStore<Long, PurrMember>() {

    override val name = "Member Store"


    operator fun get(member: Member): PurrMember? {
        return get(member.user.idLong)
    }


    fun getOrMake(member: Member): PurrMember {
        return getOrMake(member.user.idLong)
    }

    fun getOrMake(key: Long): PurrMember {
        return get(key) ?: PurrMember(guild.getMemberById(key)).apply { register(key, this) }
    }

}