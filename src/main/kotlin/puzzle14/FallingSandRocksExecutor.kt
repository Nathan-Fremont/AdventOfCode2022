package puzzle14

class FallingSandRocksExecutor {
    private var gridOfSquares = mutableListOf<MutableList<GridSquare>>()
    private var _turnCount = 0

    fun createGridWithListOfRocksPaths(listOfRocksPaths: List<List<RockPoint>>) {
        val minXValue = listOfRocksPaths
            .flatten()
            .minBy { rock ->
                rock.x
            }

        val maxXValue = listOfRocksPaths
            .flatten()
            .maxBy { rock ->
                rock.x
            }

        val delta = (maxXValue.x - minXValue.x) + 1
        println("createGridWithListOfRocksPaths ${"minXValue" to minXValue}, ${"maxXValue" to maxXValue}, ${"delta" to delta}")

        gridOfSquares = mutableListOf()
        repeat(GRID_HEIGHT) { yIndex ->
            gridOfSquares += mutableListOf<GridSquare>()
            repeat(VOID_PADDING) { voidIndex ->
                gridOfSquares.last() += GridSquare.Void(
                    originalXValue = -1,
                    xValueInGrid = voidIndex,
                    yValueInGrid = yIndex,
                )
            }
            for (xIndex in 0 until delta) {
                gridOfSquares.last() += GridSquare.Air(
                    originalXValue = minXValue.x + xIndex,
                    xValueInGrid = xIndex + VOID_PADDING,
                    yValueInGrid = yIndex,
                )
            }
            repeat(VOID_PADDING) { voidIndex ->
                gridOfSquares.last() += GridSquare.Void(
                    originalXValue = -1,
                    xValueInGrid = VOID_PADDING + delta + voidIndex,
                    yValueInGrid = yIndex,
                )
            }
        }
        repeat(VOID_PADDING) { voidIndex ->
            gridOfSquares += mutableListOf<GridSquare>()
            for (xIndex in 0 until delta + VOID_PADDING * 2) {
                gridOfSquares.last() += GridSquare.Void(
                    originalXValue = -1,
                    xValueInGrid = voidIndex,
                    yValueInGrid = gridOfSquares.size - 1,
                )
            }
        }
        println("createGridWithListOfRocksPaths ${"grid" to gridOfSquares}")

        addRocks(
            listOfRocksPaths = listOfRocksPaths,
        )
        println("createGridWithListOfRocksPaths ${"grid" to gridOfSquares}")

        addSandGenerator()
        drawGrid()
        val numberOfTurnsNeeded = simulateFallingSandForTurns()
        println("createGridWithListOfRocksPaths ${"numberOfTurnsNeeded" to numberOfTurnsNeeded}")
//        drawGrid()
    }

    private fun simulateFallingSandForTurns(numberOfTurns: Int = 5_000): Int {
        for (turnCount in 0 until numberOfTurns + 1) {
            _turnCount = turnCount
            if (!simulateSandUntilItRests()) {
                println("turnCount = $turnCount")
                drawGrid()
                return turnCount
            }
            println("turnCount = $turnCount")
            drawGrid()
        }
        return numberOfTurns
    }

    private fun simulateSandUntilItRests(): Boolean {
        val fallingSand: GridSquare.FallingSand
        val sandGenerator = gridOfSquares
            .flatten()
            .first { square ->
                square is GridSquare.FallingSandGenerator
            }
            .run {
                fallingSand = GridSquare.FallingSand(
                    originalXValue = originalXValue,
                    xValueInGrid = xValueInGrid,
                    yValueInGrid = yValueInGrid,
                )
                this
            }

        if (checkMovement(fromSquare = fallingSand) is FallingSandMovement.NoMovementPossible) {
            return false
        }

        var canMove: FallingSandMovement
        do {
            canMove = checkMovement(fromSquare = fallingSand)

            when (canMove) {
                FallingSandMovement.Bottom -> {
                    fallingSand.yValueInGrid += 1
                }

                FallingSandMovement.DiagonallyLeft -> {
                    fallingSand.xValueInGrid -= 1
                    fallingSand.yValueInGrid += 1
                }

                FallingSandMovement.DiagonallyRight -> {
                    fallingSand.xValueInGrid += 1
                    fallingSand.yValueInGrid += 1
                }

                FallingSandMovement.NoMovementPossible -> {}
                FallingSandMovement.LeadToVoid -> {
                    return false
                }
            }
        } while (canMove != FallingSandMovement.NoMovementPossible)

        gridOfSquares[fallingSand.yValueInGrid][fallingSand.xValueInGrid] = fallingSand
        return true
    }

    private fun checkMovement(fromSquare: GridSquare.FallingSand): FallingSandMovement {
        var canMove: FallingSandMovement = FallingSandMovement.NoMovementPossible
        if (gridOfSquares.size <= fromSquare.yValueInGrid + 1) {
            return canMove
        }
        val squareBelow = gridOfSquares[fromSquare.yValueInGrid + 1][fromSquare.xValueInGrid]
        val squareDiagLeft = gridOfSquares[squareBelow.yValueInGrid].getOrNull(fromSquare.xValueInGrid - 1)
        val squareDiagRight = gridOfSquares[squareBelow.yValueInGrid].getOrNull(fromSquare.xValueInGrid + 1)

        // Try moving directly below
        if (squareBelow is GridSquare.Void) {
            return FallingSandMovement.LeadToVoid
        }
        // Try moving diagonally left
        else if (squareDiagLeft is GridSquare.Void) {
            return FallingSandMovement.LeadToVoid
        }
        // Try moving diagonally right
        else if (squareDiagRight is GridSquare.Void) {
            return FallingSandMovement.LeadToVoid
        }

        // Try moving directly below
        if (squareBelow is GridSquare.Air) {
            canMove = FallingSandMovement.Bottom
        }
        // Try moving diagonally left
        else if (squareDiagLeft is GridSquare.Air) {
            canMove = FallingSandMovement.DiagonallyLeft
        }
        // Try moving diagonally right
        else if (squareDiagRight is GridSquare.Air) {
            canMove = FallingSandMovement.DiagonallyRight
        }
        return canMove
    }

    private fun addRocks(
        listOfRocksPaths: List<List<RockPoint>>,
    ) {
        println("addRocks")

        listOfRocksPaths.forEach { rockPath ->
            rockPath
                .zipWithNext()
                .forEach { pairOfRocks ->
                    createRockPath(
                        firstRockPoint = pairOfRocks.first,
                        secondRockPoint = pairOfRocks.second
                    )
                }
        }
    }

    private fun drawGrid() {
        val gridAsString = gridOfSquares.joinToString(separator = "\n") { lineOfSquares ->
            "${lineOfSquares[0].yValueInGrid} ${
                lineOfSquares.joinToString(separator = "") { square ->
                    square.stringValue
                }
            }"
        }
        println("drawGrid\n$gridAsString}")
    }

    private fun createRockPath(
        firstRockPoint: RockPoint,
        secondRockPoint: RockPoint,
    ) {
        if (firstRockPoint.y == secondRockPoint.y) {
            val (minRockPoint, maxRockPoint) = if (firstRockPoint.x < secondRockPoint.x) {
                firstRockPoint to secondRockPoint
            } else {
                secondRockPoint to firstRockPoint
            }

            createHorizontalPath(
                minRockPoint = minRockPoint,
                maxRockPoint = maxRockPoint
            )
        } else if (firstRockPoint.x == secondRockPoint.x) {
            val (minRockPoint, maxRockPoint) = if (firstRockPoint.y < secondRockPoint.y) {
                firstRockPoint to secondRockPoint
            } else {
                secondRockPoint to firstRockPoint
            }

            createVerticalPath(
                minRockPoint = minRockPoint,
                maxRockPoint = maxRockPoint
            )
        } else {
            println("else")
        }
    }

    private fun createHorizontalPath(
        minRockPoint: RockPoint,
        maxRockPoint: RockPoint,
    ) {
        var minX = 0
        var maxX = 0
        gridOfSquares.forEachIndexed { yIndex, lineOfSquares ->
            lineOfSquares.forEachIndexed { xIndex, square ->
                if (square.originalXValue == minRockPoint.x) {
                    minX = square.xValueInGrid
                }
                if (square.originalXValue == maxRockPoint.x) {
                    maxX = square.xValueInGrid
                }
            }
        }


        for (i in minX until maxX + 1) {
            val square = gridOfSquares[maxRockPoint.y][i]
            gridOfSquares[maxRockPoint.y][i] = GridSquare.Rock(
                originalXValue = square.originalXValue,
                xValueInGrid = square.xValueInGrid,
                yValueInGrid = maxRockPoint.y
            )
        }
    }

    private fun createVerticalPath(
        minRockPoint: RockPoint,
        maxRockPoint: RockPoint,
    ) {
        var minY = 0
        var maxY = 0
        var x = 0
        gridOfSquares.forEachIndexed { yIndex, lineOfSquares ->
            lineOfSquares.forEachIndexed { xIndex, square ->
                if (square.originalXValue == minRockPoint.x) {
                    x = square.xValueInGrid
                }
                if (square.yValueInGrid == minRockPoint.y) {
                    minY = square.yValueInGrid
                }
                if (square.yValueInGrid == maxRockPoint.y) {
                    maxY = square.yValueInGrid
                }
            }
        }


        for (i in minY until maxY + 1) {
            val square = gridOfSquares[maxRockPoint.y][x]
            gridOfSquares[i][x] = GridSquare.Rock(
                originalXValue = square.originalXValue,
                xValueInGrid = square.xValueInGrid,
                yValueInGrid = i
            )
        }
    }

    private fun addSandGenerator() {
        gridOfSquares
            .flatten()
            .first { square ->
                square.originalXValue == 500
                        && square.yValueInGrid == 0
            }
            .run {
                gridOfSquares[this.yValueInGrid][this.xValueInGrid] = GridSquare.FallingSandGenerator(
                    originalXValue = originalXValue,
                    xValueInGrid = xValueInGrid,
                    yValueInGrid = yValueInGrid,
                )
            }
    }

    companion object {
        private const val GRID_HEIGHT = 10
        private const val VOID_PADDING = 2
    }
}