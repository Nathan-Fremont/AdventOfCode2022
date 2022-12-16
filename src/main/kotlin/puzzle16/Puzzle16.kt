package puzzle16

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle16"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle16Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)
private val valvesParser = ValvesParser()
private val valvesExecutor = ValvesExecutor()

fun main(args: Array<String>) {
    println("main")
    fileUtils.getFileContentFromFileExample()
        ?.also { fileContent ->
            val valvesParserResult = valvesParser.parseFileContentToListOfValves(
                fileContent = fileContent,
            )

            val valvesExecutorResult = valvesExecutor.findBestPathForValves(
                valvesParserResult = valvesParserResult,
            )

//            println("valvesExecutorResult ${"valvesExecutorResult" to valvesExecutorResult}")
            val valvesOpened = valvesExecutorResult.valves.filter { valve ->
                valve.isOpened
            }.map { it.valveLabel }
            println("valvesExecutorResult ${"valvesOpened" to valvesOpened}")
            println("valvesExecutorResult ${"valvesExecutorResult.totalPressure" to valvesExecutorResult.totalPressure}")
        }
}