package puzzle02

sealed class MyPlay(val stringValue: String, val scoreValue: Int) {
    object ROCK : MyPlay(stringValue = "X", scoreValue = 1)
    object PAPER : MyPlay(stringValue = "Y", scoreValue = 2)
    object SCISSORS : MyPlay(stringValue = "Z", scoreValue = 3)

    companion object {
        private fun values(): Array<MyPlay> {
            return arrayOf(ROCK, PAPER, SCISSORS)
        }

        fun valueOf(value: String): MyPlay {
            return values()
                .firstOrNull { myPlay ->
                    myPlay.stringValue.equals(value, ignoreCase = true)
                } ?: throw IllegalArgumentException("No object puzzle02.MyPlay.$value")
        }
    }
}