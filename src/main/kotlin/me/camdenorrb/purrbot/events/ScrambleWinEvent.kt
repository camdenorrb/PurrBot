package me.camdenorrb.purrbot.events

import me.camdenorrb.purrbot.member.PurrMember
import me.camdenorrb.purrbot.tasks.ScrambleTask

data class ScrambleWinEvent(val winner: PurrMember, val scrambleTask: ScrambleTask)