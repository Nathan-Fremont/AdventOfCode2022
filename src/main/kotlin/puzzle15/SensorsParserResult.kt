package puzzle15

data class SensorsParserResult(
    val pairOfSensorsAndBeacons: List<Pair<SaBGridSquare.Sensor, SaBGridSquare.Beacon>>,
)