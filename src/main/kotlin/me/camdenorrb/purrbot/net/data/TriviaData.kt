package me.camdenorrb.purrbot.net.data

sealed class TriviaData {

    enum class Type(val code: String) {

        MULTIPLE_CHOICE("multiple"),
        TRUE_FALSE("boolean");


        companion object {

            fun byName(name: String, ignoreCase: Boolean = true) {
                Difficulty.values().find { it.name.equals(name, ignoreCase) }
            }

        }
    }

    enum class Difficulty(val code: String) {

        EASY("easy"),
        MEDIUM("medium"),
        HARD("hard");


        companion object {

            fun byName(name: String, ignoreCase: Boolean = true) {
                values().find { it.name.equals(name, ignoreCase) }
            }

        }

    }

    enum class Category(val id: Int) {

        GENERAL_KNOWLEDGE(9),

        BOOKS(10),
        FILM(11),
        MUSIC(12),
        MUSICALS_AND_THEATRES(13),
        TELEVISION(14),
        VIDEO_GAMES(15),
        BOARD_GAMES(16),

        SCIENCE_AND_NATURE(17),
        SCIENCE_COMPUTERS(18),
        SCIENCE_MATHEMATICS(19),

        MYTHOLOGY(20),
        SPORTS(21),
        GEOGRAPHY(22),
        HISTORY(23),
        POLITICS(24),
        ART(25),
        CELEBRITIES(26),
        ANIMALS(27),
        COMICS(28),
        SCIENCE_GADGETS(29),
        JAPAN_ANIME_AND_MANGA(30),
        CARTOON_AND_ANIMATIONS(31);


        companion object {

            fun byName(name: String, ignoreCase: Boolean = true) {
                Difficulty.values().find { it.name.equals(name, ignoreCase) }
            }

        }
    }

}