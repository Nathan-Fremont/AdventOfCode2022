package puzzle11

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle11"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle11Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)
private val monkeysParser = MonkeysParser()

fun main(args: Array<String>) {
    println("main")
    fileUtils.getFileContentFromFileExample()
        ?.also { fileContent ->
            val monkeys = monkeysParser.parseFileToMonkeys(
                fileContent = fileContent,
            )
        }
}