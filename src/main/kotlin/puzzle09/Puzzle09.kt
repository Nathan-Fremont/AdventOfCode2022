package puzzle09

import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle09Example"
private const val INPUT_FILE_NAME = "inputPuzzle09"

private val snakeHelper = SnakeHelper()

fun main(args: Array<String>) {
    println("Try to open file $INPUT_FILE_NAME")

    Path(INPUT_FILE_NAME)
        .takeIf { file ->
            file.exists()
        }
        ?.also { file ->
            println("Parse file")
            val fileContent = Files.readString(
                file
            )

            snakeHelper.parseInputAndExecute(fileContent = fileContent)
        }
}