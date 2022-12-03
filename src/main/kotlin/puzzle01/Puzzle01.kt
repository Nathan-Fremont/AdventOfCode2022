package puzzle01

import java.math.BigDecimal
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

private const val CHUNK_SEPARATOR = "\n\n"
private const val CHUNK_LINE_SEPARATOR = "\n"
private const val INPUT_FILE_NAME = "inputPuzzle01"

fun main(args: Array<String>) {
    println("Try to open file $INPUT_FILE_NAME")
    Path(INPUT_FILE_NAME)
        .takeIf { file ->
            file.exists()
        }
        ?.also { file ->
            println("Parse file")
            val sortedHighest = getHighestFromFile(
                Files.readString(
                    file
                )
            )
            println("==========\n${"highest" to sortedHighest}\n==========")

            val threeHighest = sortedHighest
                .take(3)
                .sumOf {
                    it
                }
            println("==========\n${"threeHighest" to threeHighest}\n==========")
        } ?: println("Error $INPUT_FILE_NAME does not exists")
}

private fun getHighestFromFile(fileLines: String): List<BigDecimal> {
    val sortedHighest = fileLines
        .split(CHUNK_SEPARATOR)
        .filter { chunk ->
            chunk.isNotBlank()
        }
        .map { chunk ->
            chunk.split(CHUNK_LINE_SEPARATOR)
        }
        .map { chunkLines ->
            chunkLines
                .filter { line ->
                    line.isNotBlank()
                }
                .sumOf { line ->
                    println("${"line" to line}")
                    line.toBigDecimal()
                }
        }
        .sortedDescending()
    return sortedHighest
}