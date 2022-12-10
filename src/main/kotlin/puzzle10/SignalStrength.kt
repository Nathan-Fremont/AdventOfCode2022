package puzzle10

data class SignalStrength(
    val cycle: Int,
    val valueAtCycle: Int,
) {
    fun calculateSignalStrength() : Int = cycle * valueAtCycle
}