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

            totalWins >= MERCURY.minWins -> MERCURY
            totalWins >= HERMES.minWins -> HERMES
            totalWins >= ORTHOGRAPHER.minWins -> ORTHOGRAPHER
            totalWins >= LITERARIAN.minWins -> LITERARIAN
            totalWins >= LITERATOR.minWins -> LITERATOR
            totalWins >= WORDSMITH.minWins -> WORDSMITH
            totalWins >= LIBRARIAN.minWins -> LIBRARIAN
            totalWins >= BOOKWORM.minWins -> BOOKWORM
            totalWins >= UNSCRAMBLER.minWins -> UNSCRAMBLER
            totalWins >= LITERATE.minWins -> LITERATE
            totalWins >= UNLETTERED.minWins -> UNLETTERED

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