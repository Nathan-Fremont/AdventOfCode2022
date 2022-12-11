package puzzle11

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle11"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle11Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)
private val monkeysParser = MonkeysParser()
private val monkeysExecutor = MonkeysExecutor()

fun main(args: Array<String>) {
    println("main")
    fileUtils.getFileContentFromFileExample()
        ?.also { fileContent ->
            val monkeys = monkeysParser.parseFileToMonkeys(
                fileContent = fileContent,
            )

            val monkeysResult = monkeysExecutor.executeMonkeysForNumberOfTurns(
                givenMonkeys = monkeys,
            )
            println("main ${"monkeysResult" to monkeysResult}")

            val twoBest = monkeysResult
                .numberOfInspectionsByMonkeys
                .sortedDescending()
                .take(2)
            println("main ${"twoBest" to twoBest}")

            val multiplyOfBest = twoBest.reduce { acc, currentElement ->
                acc * currentElement
            }
            println("main ${"multiplyOfBest" to multiplyOfBest}")

        }
}