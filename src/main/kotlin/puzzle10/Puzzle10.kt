package puzzle10

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle10"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle10Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)
private val instructionsParser = InstructionsParser()
private val instructionsExecutor = InstructionsExecutor()

fun main(args: Array<String>) {
    println("main")
    fileUtils.getFileContentFromFile()
        ?.also { fileContent ->

            val instructions = instructionsParser.parseFileToInstructions(fileContent = fileContent)
            val filteredInstructions = instructions.partition { inst ->
                inst !is Instruction.Unknown
            }
            println("main ${"filteredInstructions" to filteredInstructions.second}")

            val instructionsExecutorResult = instructionsExecutor.executeInstructions(
                instructionsToExecute = filteredInstructions.first,
            )

            println("main ${"instructionsExecutorResult" to instructionsExecutorResult}")

            val filteredSignals = instructionsExecutorResult
                .listOfSignalStrength
                .filter { signalStrength ->
                    val tempSignStrCycle = signalStrength.cycle + 20
                    tempSignStrCycle % 40 == 0
                }
            println("main ${"filteredSignals" to filteredSignals}")

            val calculatedSignals = filteredSignals
                .map { signalStrength ->
                    signalStrength.calculateSignalStrength()
                }
            println("main ${"calculatedSignals" to calculatedSignals}")

            val sumOfSignals = calculatedSignals.sum()
            println("main ${"sumOfSignals" to sumOfSignals}")

            println("===== CRT SCREEN =====")
            instructionsExecutorResult.crtScreen
                .forEach { line ->
                    println(line.joinToString(separator = "",))
                }
        }
}