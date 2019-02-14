package me.camdenorrb.purrbot.rank

enum class ScrambleRank(val displayName: String, val id: Long, val minWins: Int) {

    UNLETTERED("Unlettered", 545476283786592286, 1),
    LITERATE("Literate", 545476283988049922, 10),
    UNSCRAMBLER("Unscrambler", 545476284780642304, 20),
    BOOKWORM("BookWorm", 545476285066117120, 40),
    LIBRARIAN("Librarian", 545476285435215873, 60),
    WORDSMITH("WordSmith", 545476286118756372, 80),
    LITERATOR("Literator", 545476286840307732, 100),
    LITERARIAN("Literarian", 545476286756159498, 125),
    ORTHOGRAPHER("Orthographer", 545476287381241856, 160),
    HERMES("Hermes", 545476287649808384, 200),
    MERCURY("Mercury", 545476287909593098, 500);



    companion object {

        fun byWins(totalWins: Long): ScrambleRank? = when {

            totalWins >= 500 -> MERCURY
            totalWins >= 200 -> HERMES
            totalWins >= 160 -> ORTHOGRAPHER
            totalWins >= 125 -> LITERARIAN
            totalWins >= 100 -> LITERATOR
            totalWins >= 80 -> WORDSMITH
            totalWins >= 60 -> LIBRARIAN
            totalWins >= 40 -> BOOKWORM
            totalWins >= 20 -> UNSCRAMBLER
            totalWins >= 10 -> LITERATE
            totalWins >= 1 -> UNLETTERED

            else -> null
        }

    }

}




/*
1 - Unlettered
10 - Literate
20 - Unscrambler
40 - Bookworm
60 - Librarian
80 - Wordsmith
100 - Literator
125 - Literarian
160 - Orthographer
200 - Hermes
500 - Mercury
 */