package puzzle13

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle13"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle13Example"
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