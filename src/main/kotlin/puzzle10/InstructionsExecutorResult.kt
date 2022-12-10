package puzzle10

import FileUtils

internal data class InstructionsExecutorResult(
    val listOfSignalStrength: List<SignalStrength>,
    val crtScreen: List<List<String>>,
)