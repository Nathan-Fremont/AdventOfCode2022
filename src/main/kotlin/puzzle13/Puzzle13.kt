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
            )

            val signalsWithResult = distressSignalExecutor.executePairsOfSignals(pairsOfSignals)
            val sumOfIndices = signalsWithResult
                .filter { distressSignalResult ->
                    distressSignalResult.isInCorrectOrder
                }
                .sumOf { distressSignalResult ->
                    distressSignalResult.indexInList
                }
            println("main ${"sumOfIndices" to sumOfIndices}")
        }
}