package puzzle10

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle10"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle10Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)

fun main(args: Array<String>) {
    println("Try to open file $INPUT_FILE_NAME")

    fileUtils.getFileContentFromFile()
        ?.also { fileContent ->

        }
}