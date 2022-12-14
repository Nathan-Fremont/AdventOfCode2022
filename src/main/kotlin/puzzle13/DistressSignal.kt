package puzzle13

sealed class DistressSignal {
    data class Single(val value: Int) : DistressSignal()
    data class Multiple(val value: List<DistressSignal>) : DistressSignal()
}