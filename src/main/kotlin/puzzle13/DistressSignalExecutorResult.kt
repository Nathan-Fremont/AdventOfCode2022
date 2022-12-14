package puzzle13

data class DistressSignalExecutorResult(
    val distressSignalsResults: List<DistressSignalResult>,
    val distressSignalsInOrder: List<DistressSignal>,
)