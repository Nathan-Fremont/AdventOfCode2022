package puzzle14

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle14"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle14Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)
private val fallingSandRocksParser = FallingSandRocksParser()
private val fallingSandRocksExecutor = FallingSandRocksExecutor()

fun main(args: Array<String>) {
    println("main")
    fileUtils.getFileContentFromFile()
        ?.also { fileContent ->
            val listOfRocksPaths = fallingSandRocksParser.parseFileToListOfRocksPaths(
                fileContent = fileContent,
            )

            // Part 1 : 897
            // Part 2 : 26682
            fallingSandRocksExecutor.createGridWithListOfRocksPaths(
                listOfRocksPaths = listOfRocksPaths,
            )
        }
}