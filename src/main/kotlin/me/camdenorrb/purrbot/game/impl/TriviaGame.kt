package me.camdenorrb.purrbot.game.impl

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.minibus.event.EventWatcher
import me.camdenorrb.purrbot.game.struct.Game
import me.camdenorrb.purrbot.net.request.TriviaRequest
import me.camdenorrb.purrbot.net.response.trivia.DecodedTriviaResponse
import me.camdenorrb.purrbot.net.response.trivia.EncodedTriviaResponse
import me.camdenorrb.purrbot.utils.createEmbed
import me.camdenorrb.purrbot.variables.GSON
import me.camdenorrb.purrbot.variables.OKHTTP_CLIENT
import net.dv8tion.jda.core.entities.MessageReaction
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveAllEvent
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent
import okhttp3.Request
import java.awt.Color

class TriviaGame : Game(inject("mainChat")) {

    override val name = "Trivia"


    // User ID --> Emote Name
    val lastReactions = mutableMapOf<Long, MessageReaction>()


    @Volatile
    var currentMessageId: Long? = null
        private set

    @Volatile
    var result: DecodedTriviaResponse.Result? = null
        private set

    @Volatile
    var currentAnswers: List<String> = emptyList()
        private set


    private val countEmojis = listOf(":zero:", ":one:", ":two:", ":three:", ":four:", ":five:", ":six:", ":seven:", ":eight:", ":nine:")


    override fun onEnable() {

        val link = TriviaRequest(1).link

        val response = OKHTTP_CLIENT.newCall(Request.Builder().url(link).build()).execute().body()?.string()

        val triviaResponse = GSON.fromJson<EncodedTriviaResponse>(response, EncodedTriviaResponse::class.java).decode()

        val result = triviaResponse.results.first()

        this.result = result

        currentAnswers = listOf(result.correctAnswer!!, *(result.incorrectAnswers!!.toTypedArray())).sorted()

        sendStartMessage(result)

        startWaitingTask()

        super.onEnable()
    }

    override fun onDisable() {
        lastReactions.clear()
        super.onDisable()
    }


    @EventWatcher
    fun onReaction(event: GuildMessageReactionAddEvent) {

        val user = event.user

        val currentMessageLong = event.messageIdLong

        if (user.isBot || currentMessageId != currentMessageLong) return


        event.channel.getMessageById(event.messageId).queue {

            val reactions = it.reactions.toMutableList()
            val reactionName = event.reaction.reactionEmote.name

            val updatedReaction = reactions.removeAt(reactions.indexOfFirst { it.reactionEmote.name == reactionName })

            if (updatedReaction.count == 1) {
                updatedReaction.removeReaction(user).queue()
                return@queue
            }

            val lastReaction = lastReactions[user.idLong]

            if (lastReaction != null) {
                lastReaction.removeReaction(user).queue()
            }

            lastReactions[user.idLong] = event.reaction

            /*reactions.forEach { reaction2 ->
                reaction2.removeReaction(user).queue()
            }*/
        }
    }

    @EventWatcher
    fun onRemoveReaction(event: GuildMessageReactionRemoveEvent) {

        val user = event.user

        if (event.messageIdLong != currentMessageId || user.isBot) {
            return
        }

        if (lastReactions[user.idLong]?.reactionEmote?.name == event.reactionEmote.name) {
            lastReactions.remove(user.idLong)
        }
    }

    @EventWatcher
    fun onRemoveAllReactions(event: GuildMessageReactionRemoveAllEvent) {

        if (event.messageIdLong != currentMessageId) {
            return
        }

        disable()
    }

    @EventWatcher
    fun onMessageDelete(event: GuildMessageDeleteEvent) {

        if (event.messageIdLong != currentMessageId) {
            return
        }

        disable()
    }


    private fun getDigitEmote(number: Int): String {
        check(number in 0..9)
        return "$number\u20E3"
    }

    private fun startWaitingTask() {
        GlobalScope.launch {

            delay(15_000)

            if (!isEnabled) return@launch

            val correctAnswer = result?.correctAnswer
            val correctAnswerIndex = currentAnswers.indexOf(correctAnswer)

            val winners = lastReactions.filter { it.value.reactionEmote.name == correctAnswer }.keys.map {
                jda.getUserById(it).asTag
            }

            val embed = createEmbed {

                setColor(Color.GREEN.darker())

                setTitle("The answer is ${countEmojis[correctAnswerIndex + 1]} '$correctAnswer'")
                addField("Winners", winners.joinToString("\n"), false)
            }

            channel.sendMessage(embed).queue()

            disable()
        }
    }

    private fun sendStartMessage(result: DecodedTriviaResponse.Result) {

        val embed = createEmbed {


            val color = when (result.difficulty) {
                "easy" -> Color.GREEN
                "medium" -> Color.YELLOW
                "hard" -> Color.RED
                else -> error("Invalid difficulty")
            }

            setColor(color.darker())

            var count = 1
            addField(result.question, currentAnswers.joinToString("\n") { "${countEmojis[count++]} $it"}, false)
        }

        channel.sendMessage(embed).queue {

            repeat(currentAnswers.size) { count ->
                it.addReaction(getDigitEmote(count + 1)).queue()
            }

            currentMessageId = it.idLong
        }

    }



} //: Game() {}