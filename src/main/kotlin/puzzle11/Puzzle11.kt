package puzzle11

import FileUtils
import puzzle10.InstructionsParser

private const val INPUT_FILE_NAME = "inputPuzzle11"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle11Example"
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