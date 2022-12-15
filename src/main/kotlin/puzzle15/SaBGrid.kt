package puzzle15

data class SaBGrid(
    val gridSquares: MutableList<MutableList<SaBGridSquare>>,
    val minimumX: Long,
    val maximumX: Long,
    val minimumY: Long,
    val maximumY: Long,
) {
    val deltaX: Long = (maximumX - minimumX) + 1
    val deltaY: Long = maximumY + 1

    fun getSensors(): List<SaBGridSquare.Sensor> = gridSquares.flatMap { row ->
        row.filterIsInstance<SaBGridSquare.Sensor>()
    }

    fun getBeacons(): List<SaBGridSquare.Beacon> = gridSquares.flatMap { row ->
        row.filterIsInstance<SaBGridSquare.Beacon>()
    }
}