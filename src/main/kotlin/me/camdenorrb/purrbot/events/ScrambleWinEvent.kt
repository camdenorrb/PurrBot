package me.camdenorrb.purrbot.events

import me.camdenorrb.purrbot.tasks.ScrambleTask
import net.dv8tion.jda.api.entities.Member

data class ScrambleWinEvent(val winner: Member, val scrambleTask: ScrambleTask)