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
    private var globalCounter = 0

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

    fun calculateGridPossiblePositionsForMoreBeaconsInRow(row: Long = 0): SaBGrid {
        println("calculateGridPossiblePositionsForMoreBeacons")

//        drawGrid()

        val sensors = saBGrid.gridSquares.flatMap { list ->
            list.filterIsInstance<SaBGridSquare.Sensor>()
        }

        val squaresInRow = mutableListOf<SaBGridSquare>()
        for (paddingIndex in 0L until PADDING) {
            getSquareForRow(
                row = row,
                originalXValue = (saBGrid.minimumX - PADDING) + paddingIndex,
                xIndex = squaresInRow.size.toLong(),
            )
        }
        for (xIndex in 0 until saBGrid.deltaX) {
            getSquareForRow(
                row = row,
                originalXValue = saBGrid.minimumX + xIndex,
                xIndex = squaresInRow.size.toLong(),
            )
        }
        for (paddingIndex in 0L until PADDING) {
            getSquareForRow(
                row = row,
                originalXValue = saBGrid.maximumX + PADDING + paddingIndex,
                xIndex = squaresInRow.size.toLong(),
            )
        }
//        saBGrid.gridSquares += squaresInRow
        val possible = squaresInRow.filter { it -> it is SaBGridSquare.None && it.isBeaconPossible }
        println("calculateGridPossiblePositionsForMoreBeacons ${"possible" to possible}")
        println("calculateGridPossiblePositionsForMoreBeacons ${"globalCounter" to globalCounter}")
//        drawGrid()

        return saBGrid
    }

    private fun getSquareForRow(row: Long, originalXValue: Long, xIndex: Long): SaBGridSquare {
        var newSquare = SaBGridSquare.None(
            originalXValue = originalXValue,
            originalYValue = row,
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
        val distanceBetweenSensorAndSquares = saBGrid.getSensors().map { sensor ->
            sensor.getDistanceBetweenTwoSquares(otherSquare = newSquare)
        }
        val distancesToClosestBeacon = saBGrid.getSensors().map { sensor ->
            sensor.distanceToClosestBeacon
        }
        val isBeaconPossible = saBGrid.getSensors().all { sensor ->
            val distanceBetweenSensorAndSquare = sensor.getDistanceBetweenTwoSquares(otherSquare = newSquare)
            val isPossible = distanceBetweenSensorAndSquare > sensor.distanceToClosestBeacon
            isPossible
        }
        newSquare = newSquare.copy(
            distanceToClosestSensor = distanceToClosestSensor,
            distanceToClosestBeacon = distanceToClosestBeacon,
            isBeaconPossible = isBeaconPossible,
        )
        return if (distanceToClosestBeacon == 0L) {
            SaBGridSquare.Beacon(
                originalXValue = newSquare.originalXValue,
                originalYValue = newSquare.originalYValue,
                xValueInGrid = newSquare.xValueInGrid,
                yValueInGrid = newSquare.yValueInGrid,
                distanceToClosestSensor = newSquare.distanceToClosestSensor,
                distanceToClosestBeacon = newSquare.distanceToClosestBeacon,
                isBeaconPossible = false
            )
        } else if (distanceToClosestSensor == 0L) {
            SaBGridSquare.Sensor(
                originalXValue = newSquare.originalXValue,
                originalYValue = newSquare.originalYValue,
                xValueInGrid = newSquare.xValueInGrid,
                yValueInGrid = newSquare.yValueInGrid,
                distanceToClosestSensor = newSquare.distanceToClosestSensor,
                distanceToClosestBeacon = newSquare.distanceToClosestBeacon,
                isBeaconPossible = false
            )
        } else {
            if (!newSquare.isBeaconPossible) {
                globalCounter += 1
            }
            newSquare
        }
    }

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
        private const val PADDING = 5_000L
    }
}