package puzzle15

import FileUtils

class SensorsParser {
    fun parseFileContentToListOfSensors(fileContent: String): SensorsParserResult {
        println("parseFileContentToListOfSensors")
        val fileLines = FileUtils
            .splitStringWithDelimiter(
                fileContent = fileContent,
            )

        val pairOfSensorsAndBeacons = parseLinesToSensorsAndBeacons(
            fileLines = fileLines
        )

        return SensorsParserResult(
            pairOfSensorsAndBeacons = pairOfSensorsAndBeacons,
        )
    }

    private fun parseLinesToSensorsAndBeacons(fileLines: List<String>): List<Pair<SaBGridSquare.Sensor, SaBGridSquare.Beacon>> {
        println("parseLinesToSensorsAndBeacons")
        val sensorsAndBeaconsAsString = fileLines.flatMap { line ->
            FileUtils
                .splitStringWithDelimiter(
                    fileContent = line,
                    delimiter = SENSORS_AND_BEACONS_DELIMITER,
                )
        }

        val pairOfSensorsAndBeacons = sensorsAndBeaconsAsString
            .chunked(2)
            .map { line ->
                val sensor = parseSensor(
                    sensorAsString = line[0],
                )!!
                val beacon = parseBeacon(
                    beaconAsString = line[1]
                )!!
                val distance = sensor.getDistanceBetweenTwoSquares(otherSquare = beacon)
                sensor.copy(
                    distanceToClosestBeacon = distance
                ) to beacon.copy(
                    distanceToClosestSensor = distance,
                )
            }


        return pairOfSensorsAndBeacons
    }

    private fun parseSensor(sensorAsString: String): SaBGridSquare.Sensor? {
        println("parseSensor ${"sensorAsString" to sensorAsString}")
        val regex = Regex(
            COORDINATES_REGEX
        )

        return regex
            .find(sensorAsString)
            ?.groupValues
            ?.run {
                SaBGridSquare.Sensor(
                    originalXValue = this[1].toLong(),
                    originalYValue = this[2].toLong(),
                )
            } ?: run {
            println("parseSensor null")
            null
        }
    }

    private fun parseBeacon(beaconAsString: String): SaBGridSquare.Beacon? {
        println("parseBeacon ${"beaconAsString" to beaconAsString}")
        val regex = Regex(
            COORDINATES_REGEX
        )

        return regex
            .find(beaconAsString)
            ?.groupValues
            ?.run {
                SaBGridSquare.Beacon(
                    originalXValue = this[1].toLong(),
                    originalYValue = this[2].toLong(),
                )
            } ?: run {
            println("parseBeacon null")
            null
        }
    }

    companion object {
        private const val SENSORS_AND_BEACONS_DELIMITER = ":"
        private const val SENSOR_STRING_VALUE = "sensor"
        private const val BEACON_STRING_VALUE = "beacon"
        private const val COORDINATES_REGEX = "x=([-\\d]+), y=([-\\d]+)"
    }
}