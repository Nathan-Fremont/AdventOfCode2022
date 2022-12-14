package puzzle13

import FileUtils
import java.io.File

class DistressSignalParser {
    fun parseFileContentToDistressSignals(fileContent: String): List<Pair<DistressSignal?, DistressSignal?>> {
        println("parseFileContentToDistressSignals")
        val fileLines = FileUtils.splitStringWithDelimiter(
            fileContent = fileContent,
        )

        val pairsOfSignals = fileLines
            .chunked(2)
            .map { pairOfSignals ->
                findArray(
                    distressSignalAsString = pairOfSignals[0],
                ) to findArray(
                    distressSignalAsString = pairOfSignals[1],
                )
            }
        return pairsOfSignals
    }

    private fun findArray(distressSignalAsString: String?): DistressSignal? {
        if (distressSignalAsString.isNullOrBlank()) {
            return null
        }
        var arrayCount = ARRAY_ROOT_LAYER
        val modifiedString = distressSignalAsString
            .map { char ->
                if (char == START_ARRAY) {
                    arrayCount += 1
                } else if (char == END_ARRAY) {
                    arrayCount -= 1
                }
                if (char == VALUE_DELIMITER
                    && arrayCount == 1
                ) {
                    ARRAY_ROOT_LAYER_DELIMITER
                } else {
                    char
                }
            }
            .joinToString(separator = "")
            .removePrefix(START_ARRAY.toString())
            .removeSuffix(END_ARRAY.toString())
//        println("findArray ${"modifiedString" to modifiedString}")

        val arrayOfModified = FileUtils
            .splitStringWithDelimiter(
                modifiedString,
                delimiter = ARRAY_ROOT_LAYER_DELIMITER.toString(),
            )
//        println("findArray ${"arrayOfModified" to arrayOfModified}")

        val distressed = arrayOfModified
            .map { value ->
                if (value.contains(START_ARRAY)) {
                    return@map findArray(
                        distressSignalAsString = value,
                    )
                } else {
                    return@map DistressSignal.Single(
                        value = value.toInt()
                    )
                }
            }

        return DistressSignal.Multiple(
            value = distressed.filterNotNull(),
        )
    }

    companion object {
        private const val START_ARRAY = '['
        private const val END_ARRAY = ']'
        private const val VALUE_DELIMITER = ','
        private const val ARRAY_ROOT_LAYER = 0
        private const val ARRAY_ROOT_LAYER_DELIMITER = '#'
    }
}