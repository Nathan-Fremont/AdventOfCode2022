package puzzle16

import FileUtils

class ValvesParser {
    fun parseFileContentToListOfValves(fileContent: String): ValvesParserResult {
        println("parseFileContentToListOfValves")
        val fileLines = FileUtils
            .splitStringWithDelimiter(
                fileContent = fileContent,
            )

        val listOfValves = fileLines
            .mapNotNull { line ->
                parseLineToValve(line = line)
            }

        listOfValves.forEachIndexed { valveIndex, valve ->
            valve.tunnelsToLabels.forEachIndexed { tunnelIndex, destinationLabel ->
                listOfValves[valveIndex].tunnelsToValve += listOfValves.first { tmpValve ->
                    tmpValve.valveLabel == destinationLabel
                }
            }
        }

        return ValvesParserResult(
            valves = listOfValves,
        )
    }

    private fun parseLineToValve(line: String): Valve? {
        println("parseLineToValve")
        val regex = Regex(
            pattern = VALVE_REGEX,
        )
        return regex
            .find(input = line)
            ?.groupValues
            ?.run {
                Valve(
                    lineValue = line,
                    valveLabel = this[1],
                    flowRate = this[2].toInt(),
                    tunnelsToLabels = this[3].run {
                        FileUtils.splitStringWithDelimiter(
                            fileContent = this,
                            delimiter = TUNNELS_TO_VALVES_DELIMITER,
                        )
                    },
                    tunnelsToValve = mutableListOf(),
                )
            }
    }

    companion object {
        private const val VALVE_REGEX = "Valve (\\w+).+rate=(\\d+);.+valves* ([\\w, ]+)+"
        private const val TUNNELS_TO_VALVES_DELIMITER = ","
    }
}