package me.camdenorrb.purrbot.net.response.trivia

import com.google.gson.annotations.SerializedName
import me.camdenorrb.purrbot.utils.fromBase

data class EncodedTriviaResponse(val responseCode: Int, val results: List<Result>) {

    fun decode(): DecodedTriviaResponse {
        return DecodedTriviaResponse(responseCode, results.map { it.decode() })
    }


    data class Result(val category: String?, val type: String?, val difficulty: String?, val question: String?, @SerializedName("correct_answer") val correctAnswer: String?, @SerializedName("incorrect_answers") val incorrectAnswers: List<String>?) {

        fun decode(): DecodedTriviaResponse.Result {

            val category = category.fromBase()
            val type = type.fromBase()
            val difficulty = difficulty.fromBase()
            val question = question.fromBase()
            val correctAnswer = correctAnswer.fromBase()

            val incorrectAnswers = incorrectAnswers?.map { it.fromBase() }

            return DecodedTriviaResponse.Result(category, type, difficulty, question, correctAnswer, incorrectAnswers)
        }

    }

}

data class DecodedTriviaResponse(val responseCode: Int, val results: List<Result>) {

    data class Result(val category: String?, val type: String?, val difficulty: String?, val question: String?, val correctAnswer: String?, val incorrectAnswers: List<String>?)

}