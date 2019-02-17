package me.camdenorrb.purrbot.events.scramble

import me.camdenorrb.purrbot.game.impl.ScrambleGame
import me.camdenorrb.purrbot.store.MemberStore

data class ScrambleWinEvent(val winner: MemberStore.PurrMember, val game: ScrambleGame)