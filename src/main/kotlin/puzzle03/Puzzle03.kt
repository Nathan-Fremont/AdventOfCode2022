package puzzle03

import puzzle02.*
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

private const val INPUT_FILE_NAME = "inputPuzzle03"

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
        } ?: println("Error $INPUT_FILE_NAME does not exists")
}