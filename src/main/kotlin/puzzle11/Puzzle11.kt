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
    fileUtils.getFileContentFromFile()
        ?.also { fileContent ->
            val monkeys = monkeysParser.parseFileToMonkeys(
                fileContent = fileContent,
            )

            val monkeysResult = monkeysExecutor.executeMonkeysForNumberOfTurns(
                givenMonkeys = monkeys,
                numberOfTurns = 10_000, // Should be 20 in Part 1
            )
            println("main ${"monkeysResult" to monkeysResult}")

            val twoBest = monkeysResult
                .numberOfInspectionsByMonkeys
                .mapIndexed { index, number ->
                    index to number.toBigInteger()
                }
                .sortedByDescending { pair ->
                    pair.second
                }
            println("main ${"twoBest" to twoBest}")

            val multiplyOfBest = twoBest
                .take(2)
                .map { pair ->
                    pair.second
                }
                .reduce { acc, currentElement ->
                    acc * currentElement
                }
            println("main ${"multiplyOfBest" to multiplyOfBest}")

        }
}