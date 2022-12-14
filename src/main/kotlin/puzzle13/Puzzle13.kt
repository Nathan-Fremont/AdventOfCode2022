package puzzle13

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle13"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle13Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)
private val distressSignalParser = DistressSignalParser()
private val distressSignalExecutor = DistressSignalExecutor()

fun main(args: Array<String>) {
    println("main")
    fileUtils.getFileContentFromFile()
        ?.also { fileContent ->
            val pairsOfSignals = distressSignalParser.parseFileContentToDistressSignals(
                fileContent = fileContent,
                addDecoderKeys = true,
            )

            val distressSignalExecutorResult = distressSignalExecutor.executePairsOfSignals(
                pairsOfSignals = pairsOfSignals,
            )
            // Part1 : 5252
            val signalsInOrder = distressSignalExecutorResult
                .distressSignalsResults
                .filter { distressSignalResult ->
                    distressSignalResult.isInCorrectOrder
                }
            val indices = signalsInOrder
                .map { signal ->
                    signal.indexInList
                }
            val sumOfIndices = indices.sum()
            println("main ${"sumOfIndices" to sumOfIndices}")

            // Part2 :
            val foundDecoderKeys = distressSignalExecutorResult
                .distressSignalsInOrder
                .mapIndexed { index, distressSignal ->
                    if (distressSignal.isDecoderKey) {
                        index + 1
                    } else {
                        1
                    }
                }
            println("main ${"foundDecoderKeys" to foundDecoderKeys}")
            val multipliedDecoderKeysIndex = foundDecoderKeys
                .reduce { acc, i ->
                    acc * i
                }
            println("main ${"multipliedDecoderKeysIndex" to multipliedDecoderKeysIndex}")
        }
}