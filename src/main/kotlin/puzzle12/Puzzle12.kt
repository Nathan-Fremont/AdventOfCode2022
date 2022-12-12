package puzzle12

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle12"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle12Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)
private val hillClimbingParser = HillClimbingParser()
private val hillClimbingExecutor = HillClimbingExecutor()

fun main(args: Array<String>) {
    println("main")
    fileUtils.getFileContentFromFile()
        ?.also { fileContent ->
            val gridOfHills = hillClimbingParser.parseFileContentToGridOfHills(
                fileContent = fileContent
            )

            val hillPath = hillClimbingExecutor.findPathInGridOfHills(
                gridOfHills = gridOfHills,
            )

            val bestStartForPart2 = hillPath
                .filter { hillWithCost ->
                    hillWithCost.hill.elevation == 0
                }
                .minBy { hillWithCost ->
                    hillWithCost.cost
                }
            println("main ${"bestStartForPart2" to bestStartForPart2}")
        }
}