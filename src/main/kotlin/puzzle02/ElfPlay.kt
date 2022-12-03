package puzzle02

sealed class ElfPlay(val stringValue: String, val scoreValue: Int) {
    object ROCK : ElfPlay(stringValue = "A", scoreValue = 1)
    object PAPER : ElfPlay(stringValue = "B", scoreValue = 2)
    object SCISSORS : ElfPlay(stringValue = "C", scoreValue = 3)

    companion object {
        private fun values(): Array<ElfPlay> {
            return arrayOf(ROCK, PAPER, SCISSORS)
        }

        fun valueOf(value: String): ElfPlay {
            return values()
                .firstOrNull { elfPlay ->
                    elfPlay.stringValue.equals(value, ignoreCase = true)
                } ?: throw IllegalArgumentException("No object puzzle02.ElfPlay.$value")
        }
    }
}