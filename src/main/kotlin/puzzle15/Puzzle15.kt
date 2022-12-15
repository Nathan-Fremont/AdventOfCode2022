package puzzle15

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle15"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle15Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)

fun main(args: Array<String>) {
    println("main")
    fileUtils.getFileContentFromFile()
        ?.also { fileContent ->

        }
}