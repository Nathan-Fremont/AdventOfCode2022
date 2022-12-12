package puzzle12

import FileUtils

class HillClimbingParser {
    fun parseFileContentToGridOfHills(fileContent: String): List<List<Hill>> {
        println("parseFileContentToGridOfHills")
        val fileLines = FileUtils.splitStringWithDelimiter(
            fileContent = fileContent,
        )

        val gridOfHills = fileLines
            .mapIndexed { yIndex, line ->
                line.mapIndexed { xIndex, char ->
                    Hill(
                        stringValue = char.toString(),
                        isStart = char == START_CHAR,
                        isEnd = char == END_CHAR,
                        elevation = when (char) {
                            START_CHAR -> {
                                0
                            }

                            END_CHAR -> {
                                25
                            }

                            else -> {
                                char.lowercaseChar() - SMALLEST_ELEVATION
                            }
                        },
                        xValue = xIndex,
                        yValue = yIndex,
                    )
                }
            }
        println("parseFileContentToGridOfHills ${"gridOfHills" to gridOfHills}")

        return gridOfHills
    }

    companion object {
        private const val START_CHAR = 'S'
        private const val END_CHAR = 'E'
        private const val SMALLEST_ELEVATION = 'a'
    }
}