package me.camdenorrb.purrbot.store

import me.camdenorrb.kcommons.store.struct.MappedStore
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member

class MemberStore(val guild: Guild, val scramblerStore: ScramblerStore) : MappedStore<Long, MemberStore.PurrMember>() {

    override val name = "Member Store"


    operator fun get(member: Member): PurrMember? {
        return get(member.user.idLong)
    }


    fun getOrMake(member: Member): PurrMember {
        val id = member.user.idLong
        return get(id) ?: PurrMember(member).apply { register(id, this) }
    }

    fun getOrMake(key: Long): PurrMember {
        return get(key) ?: PurrMember(guild.getMemberById(key)).apply { register(key, this) }
    }


    inner class PurrMember internal constructor(val member: Member) {

        val scramblerData by lazy {
            scramblerStore.getOrMake(member)
        }

    }

}