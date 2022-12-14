package puzzle14

import FileUtils
import kotlin.math.roundToInt

class FallingSandRocksParser {
    fun parseFileToListOfRocksPaths(fileContent: String): List<List<RockPoint>> {
        println("parseFileToRocks")
        val fileLines = FileUtils
            .splitStringWithDelimiter(
                fileContent = fileContent.replace(",", "."),
            )

        val listOfRocksPaths = fileLines
            .map { line ->
            val listOfRockPoints = FileUtils.splitStringWithDelimiter(
                fileContent = line,
                delimiter = ROCK_POINTS_DELIMITER,
            )

            listOfRockPoints.map { rockPoint ->
                val rockPointAsDecimal = rockPoint.toFloat()
                val rockPointAsInt = rockPointAsDecimal.toInt()
                RockPoint(
                    x = rockPointAsInt,
                    y = ((rockPointAsDecimal - rockPointAsInt.toFloat()) * 10f).roundToInt()
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