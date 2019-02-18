package me.camdenorrb.purrbot.net.request

import me.camdenorrb.purrbot.net.data.TriviaData.*
import kotlin.random.Random

class TriviaRequest(val amount: Int, val type: Type? = null, val category: Category? = null, val difficulty: Difficulty? = null) {

    val link by lazy {
        generateLink()
    }


    private fun generateLink(): String {
        return buildString {
            append("$BASE_API_URL&amount=$amount")
            if (category != null) append("&category=${category.id}")
            if (difficulty != null) append("&difficulty=${difficulty.code}")
            if (type != null) append("&type=${type.code}")
        }
    }


    companion object {

        const val BASE_API_URL = "https://opentdb.com/api.php?encode=base64"


        fun randomRequest(minAmount: Int, maxAmount: Int = minAmount, type: Type? = null, category: Category? = null, difficulty: Difficulty? = null): TriviaRequest {

            val amount1 = if (minAmount == maxAmount) minAmount else Random.nextInt(minAmount, maxAmount)
            val type1 = type ?: Type.values().random()
            val category1 = category ?: Category.values().random()
            val difficulty1 = difficulty ?: Difficulty.values().random()

            return TriviaRequest(amount1, type1, category1, difficulty1)
        }

    }

}