package puzzle14

import FileUtils
import kotlin.math.roundToInt

class FallingSandRocksParser {
    fun parseFileToListOfRocksPaths(fileContent: String): List<List<RockPoint>> {
        println("parseFileToRocks")
        val fileLines = FileUtils
            .splitStringWithDelimiter(
                fileContent = fileContent
            )

        val listOfRocksPaths = fileLines
            .map { line ->
                val listOfRockPoints = FileUtils.splitStringWithDelimiter(
                    fileContent = line,
                    delimiter = ROCK_POINTS_DELIMITER,
                )

                listOfRockPoints.map { rockPoint ->
                    val splittedRockPoint = FileUtils
                        .splitStringWithDelimiter(
                            fileContent = rockPoint,
                            delimiter = ",",
                        )
                    RockPoint(
                        x = splittedRockPoint[0].toInt(),
                        y = splittedRockPoint[1].toInt()
                    )
                }
            }
        println("parseFileToRocks ${"listOfRocksPaths" to listOfRocksPaths}")

        return listOfRocksPaths
    }

    companion object {
        private const val ROCK_POINTS_DELIMITER = "->"
    }
}