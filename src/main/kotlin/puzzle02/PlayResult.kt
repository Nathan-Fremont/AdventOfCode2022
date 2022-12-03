package puzzle02

sealed class PlayResult(val stringValue: String, val scoreValue: Int) {
    object WIN : PlayResult(stringValue = "Z", scoreValue = 6)
    object DRAW : PlayResult(stringValue = "Y", scoreValue = 3)
    object LOSS : PlayResult(stringValue = "X", scoreValue = 0)

    companion object {
        private fun values(): Array<PlayResult> {
            return arrayOf(WIN, DRAW, LOSS)
        }

        fun valueOf(value: String): PlayResult {
            return values()
                .firstOrNull { playResult ->
                    playResult.stringValue.equals(value, ignoreCase = true)
                } ?: throw IllegalArgumentException("No object puzzle02.PlayResult.$value")
        }
    }
}