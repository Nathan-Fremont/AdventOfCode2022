package puzzle13

data class DistressSignalResult(
    val leftSignal: DistressSignal?,
    val rightSignal: DistressSignal?,
    val indexInList: Int,
    val isInCorrectOrder: Boolean,
)