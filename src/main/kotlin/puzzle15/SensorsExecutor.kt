package puzzle15

class SensorsExecutor {
    private var saBGrid = SaBGrid(
        gridSquares = mutableListOf(),
        minimumX = 0,
        maximumX = 0,
        minimumY = 0,
        maximumY = 0,
    )
    private var squaresInspected = mutableListOf<SaBGridSquare>()
    private var squaresToInspect = mutableListOf<SaBGridSquare>()

    fun createGridFromSensorsAndBeacons(sensorsParserResult: SensorsParserResult) {
        println("createGridFromSensorsAndBeacons")

        val allSensors: List<SaBGridSquare.Sensor> = sensorsParserResult
            .pairOfSensorsAndBeacons.map { pair ->
                pair.first
            }
        val allBeacons: List<SaBGridSquare.Beacon> = sensorsParserResult
            .pairOfSensorsAndBeacons.map { pair ->
                pair.second
            }
        val beaconsAndSensors: List<SaBGridSquare> = listOf(
            allSensors,
            allBeacons,
        ).flatten()

        val minX = beaconsAndSensors.minBy { saBGridSquare ->
            saBGridSquare.originalXValue
        }.originalXValue
        val maxX = beaconsAndSensors.maxBy { saBGridSquare ->
            saBGridSquare.originalXValue
        }.originalXValue
        val minY = beaconsAndSensors.minBy { saBGridSquare ->
            saBGridSquare.originalYValue
        }.originalYValue
        val maxY = beaconsAndSensors.maxBy { saBGridSquare ->
            saBGridSquare.originalYValue
        }.originalYValue

        saBGrid = SaBGrid(
            gridSquares = mutableListOf(),
            minimumX = minX,
            maximumX = maxX,
            minimumY = minY,
            maximumY = maxY,
        )

        saBGrid.gridSquares += mutableListOf<SaBGridSquare>()
        beaconsAndSensors.forEach { sensor ->
            saBGrid.gridSquares.last() += sensor
        }

        println("createGridFromSensorsAndBeacons ${"deltaX" to saBGrid.deltaX}, ${"deltaY" to saBGrid.deltaY}")
//        repeat(saBGrid.deltaY) { yIndex ->
//            saBGrid.gridSquares += mutableListOf<SaBGridSquare>()
//
//            // Left padding
//            repeat(GRID_HORIZONTAL_PADDING) { paddingIndex ->
//                saBGrid.gridSquares.last() += SaBGridSquare.None(
//                    originalXValue = -1,
//                    originalYValue = -1,
//                    xValueInGrid = paddingIndex,
//                    yValueInGrid = yIndex,
//                )
//            }
//
//            // Values
//            repeat(saBGrid.deltaX) { xIndex ->
//                var newSquare = SaBGridSquare.None(
//                    originalXValue = minX + xIndex,
//                    originalYValue = yIndex,
//                    xValueInGrid = GRID_HORIZONTAL_PADDING + xIndex,
//                    yValueInGrid = yIndex,
//                )
//
//                newSquare = newSquare.copy(
//                    distanceToClosestSensor = allSensors.minOf { sensor ->
//                        sensor.getDistanceBetweenTwoSquares(newSquare)
//                    },
//                    distanceToClosestBeacon = allBeacons.minOf { beacon ->
//                        beacon.getDistanceBetweenTwoSquares(newSquare)
//                    },
//                )
//                saBGrid.gridSquares.last() += newSquare
//                beaconsAndSensors
//                    .find { saBGridSquare ->
//                        saBGridSquare.isOnSameOriginalPosition(newSquare)
//                    }?.apply {
//                        saBGrid.gridSquares.last().removeLast()
//                        saBGrid.gridSquares.last() += when (this) {
//                            is SaBGridSquare.Beacon -> this.copy(
//                                xValueInGrid = newSquare.xValueInGrid,
//                                yValueInGrid = newSquare.yValueInGrid,
//                            )
//
//                            is SaBGridSquare.None -> this
//                            is SaBGridSquare.Sensor -> this.copy(
//                                xValueInGrid = newSquare.xValueInGrid,
//                                yValueInGrid = newSquare.yValueInGrid,
//                            )
//                        }
//                    }
//            }
//
//            // Right padding
//            repeat(GRID_HORIZONTAL_PADDING) { paddingIndex ->
//                saBGrid.gridSquares.last() += SaBGridSquare.None(
//                    originalXValue = -1,
//                    originalYValue = -1,
//                    xValueInGrid = saBGrid.gridSquares.last().size,
//                    yValueInGrid = yIndex,
//                )
//            }
//        }
    }

    fun calculateGridPossiblePositionsForMoreBeaconsInRow(row: Int = 0): SaBGrid {
        println("calculateGridPossiblePositionsForMoreBeacons")

//        drawGrid()

        val sensors = saBGrid.gridSquares.flatMap { list ->
            list.filterIsInstance<SaBGridSquare.Sensor>()
        }

        val squaresInRow = mutableListOf<SaBGridSquare>()
        for (xIndex in 0 until saBGrid.deltaX) {
            var newSquare = SaBGridSquare.None(
                originalXValue = saBGrid.minimumX + xIndex,
                originalYValue = saBGrid.minimumY + row,
                xValueInGrid = xIndex,
                yValueInGrid = saBGrid.gridSquares.size.toLong(),
                distanceToClosestSensor = 0,
                distanceToClosestBeacon = 0,
                isBeaconPossible = true
            )

            val distanceToClosestSensor = saBGrid.getSensors().minOf { sensor ->
                sensor.getDistanceBetweenTwoSquares(newSquare)
            }
            val distanceToClosestBeacon = saBGrid.getBeacons().minOf { sensor ->
                sensor.getDistanceBetweenTwoSquares(newSquare)
            }
//            val closestSensor = sensors.minBy { sensor ->
//                sensor.getDistanceBetweenTwoSquares(otherSquare = newSquare)
//            }
            val isBeaconPossible = sensors.all { sensor ->
                val distanceBetweenSensorAndSquare = sensor.getDistanceBetweenTwoSquares(otherSquare = newSquare)
                distanceBetweenSensorAndSquare > sensor.distanceToClosestBeacon
            }
            newSquare = newSquare.copy(
                distanceToClosestSensor = distanceToClosestSensor,
                distanceToClosestBeacon = distanceToClosestBeacon,
                isBeaconPossible = isBeaconPossible,
            )
            if (distanceToClosestBeacon == 0L) {
                squaresInRow += SaBGridSquare.Beacon(
                    originalXValue = newSquare.originalXValue,
                    originalYValue = newSquare.originalYValue,
                    xValueInGrid = newSquare.xValueInGrid,
                    yValueInGrid = newSquare.yValueInGrid,
                    distanceToClosestSensor = newSquare.distanceToClosestSensor,
                    distanceToClosestBeacon = newSquare.distanceToClosestBeacon,
                    isBeaconPossible = false
                )
            } else if (distanceToClosestSensor == 0L) {
                squaresInRow += SaBGridSquare.Sensor(
                    originalXValue = newSquare.originalXValue,
                    originalYValue = newSquare.originalYValue,
                    xValueInGrid = newSquare.xValueInGrid,
                    yValueInGrid = newSquare.yValueInGrid,
                    distanceToClosestSensor = newSquare.distanceToClosestSensor,
                    distanceToClosestBeacon = newSquare.distanceToClosestBeacon,
                    isBeaconPossible = false
                )
            } else {
                squaresInRow += newSquare
            }
        }
        saBGrid.gridSquares += squaresInRow

//        saBGrid.gridSquares.forEachIndexed { yIndex, row ->
//            row.forEachIndexed { xIndex, saBGridSquare ->
//                if (saBGridSquare is SaBGridSquare.None) {
//                    val minDistance = sensors.minBy { sensor ->
//                        sensor.getDistanceBetweenTwoSquares(otherSquare = saBGridSquare)
//                    }
//
//                    println("minDistance = $minDistance")
//                    saBGrid.gridSquares[yIndex][xIndex].isBeaconPossible = (saBGridSquare.distanceToClosestSensor >= minDistance.distanceToClosestBeacon)
//                }
//            }
//        }

//        val sensor = sensors[6]
//        val distance = sensor.distanceToClosestBeacon
//        println("calculateGridPossiblePositionsForMoreBeacons ${"distance" to distance}")
//        propagateFromSquare(
//            saBGridSquare = sensor,
//            maxDistance = distance
//        )

//        sensors.forEach { sensor ->
//            val distance = sensor.distanceToClosestBeacon
//            println("calculateGridPossiblePositionsForMoreBeacons ${"distance" to distance}")
//            propagateFromSquare(
//                saBGridSquare = sensor,
//                maxDistance = distance + 1
//            )
//        }

        drawGrid()

        return saBGrid
    }

//    private fun propagateFromSquare(saBGridSquare: SaBGridSquare, maxDistance: Int) {
//        squaresInspected += saBGridSquare
//        val test = saBGrid.getSensors()
//        if (maxDistance <= 0) {
//            return
//        }
//        if (saBGridSquare is SaBGridSquare.None) {
//            saBGrid.gridSquares[saBGridSquare.yValueInGrid][saBGridSquare.xValueInGrid] = saBGridSquare.copy(
//                isBeaconPossible = false,
//            )
//        }
//        val newSquaresToInspect = mutableListOf<SaBGridSquare>()
//        // Top
//        saBGrid.gridSquares
//            .getOrNull(saBGridSquare.yValueInGrid - 1)
//            ?.getOrNull(saBGridSquare.xValueInGrid)
//            ?.run {
//                newSquaresToInspect += this
//            }
//
//        // Left
//        saBGrid.gridSquares
//            .getOrNull(saBGridSquare.yValueInGrid)
//            ?.getOrNull(saBGridSquare.xValueInGrid - 1)
//            ?.run {
//                newSquaresToInspect += this
//            }
//
//        // Right
//        saBGrid.gridSquares
//            .getOrNull(saBGridSquare.yValueInGrid)
//            ?.getOrNull(saBGridSquare.xValueInGrid + 1)
//            ?.run {
//                newSquaresToInspect += this
//            }
//
//        // Bottom
//        saBGrid.gridSquares
//            .getOrNull(saBGridSquare.yValueInGrid + 1)
//            ?.getOrNull(saBGridSquare.xValueInGrid)
//            ?.run {
//                newSquaresToInspect += this
//            }
//
//        newSquaresToInspect
//            .run {
//                forEach { squareAdded ->
//                    propagateFromSquare(
//                        saBGridSquare = squareAdded,
//                        maxDistance = maxDistance - 1
//                    )
//                }
//            }
//
//        newSquaresToInspect
//            .forEach { squareAdded ->
////                squaresToInspect.removeFirst()
//
//            }
//    }


    private fun drawGrid() {
        val rowIndicatorPadding = saBGrid.deltaY.toString().length
        // Grid
        saBGrid.gridSquares.forEachIndexed { yIndex, row ->
            // Row indicator
            print("${yIndex.toString().padStart(rowIndicatorPadding)}: ")

            // Row values
            row.forEach { square ->
                print(square.stringValue)
            }
            println()
        }
    }

    companion object {
        private const val GRID_HORIZONTAL_PADDING = 0
    }
}