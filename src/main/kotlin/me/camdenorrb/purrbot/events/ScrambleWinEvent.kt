package me.camdenorrb.purrbot.events

import me.camdenorrb.purrbot.store.MemberStore
import me.camdenorrb.purrbot.tasks.ScrambleTask

data class ScrambleWinEvent(val winner: MemberStore.PurrMember, val scrambleTask: ScrambleTask)