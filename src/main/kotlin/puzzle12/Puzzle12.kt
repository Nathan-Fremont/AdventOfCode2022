package puzzle12

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle12"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle12Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)
fun main(args: Array<String>) {
    println("main")
    fileUtils.getFileContentFromFileExample()
        ?.also { fileContent ->

        }
}