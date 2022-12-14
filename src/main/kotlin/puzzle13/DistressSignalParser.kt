package puzzle13

import FileUtils

class DistressSignalParser {
    fun parseFileContentToDistressSignals(
        fileContent: String,
        addDecoderKeys: Boolean = false,
    ): List<Pair<DistressSignal?, DistressSignal?>> {
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
            .toMutableList()

        if (addDecoderKeys) {
            DECODER_KEYS.forEach { decoderKey ->
                pairsOfSignals += findArray(
                    distressSignalAsString = decoderKey
                ) to null
            }
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
                        stringValue = value,
                        isDecoderKey = DECODER_KEYS.contains(value),
                        value = value.toInt()
                    )
                }
            }

        return DistressSignal.Multiple(
            stringValue = distressSignalAsString,
            isDecoderKey = DECODER_KEYS.contains(distressSignalAsString),
            value = distressed.filterNotNull(),
        )
    }

    companion object {
        private const val START_ARRAY = '['
        private const val END_ARRAY = ']'
        private const val VALUE_DELIMITER = ','
        private const val ARRAY_ROOT_LAYER = 0
        private const val ARRAY_ROOT_LAYER_DELIMITER = '#'
        private val DECODER_KEYS = listOf<String>(
            "[[2]]",
            "[[6]]",
        )
    }
}