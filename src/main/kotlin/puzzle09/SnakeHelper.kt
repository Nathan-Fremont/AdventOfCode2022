package puzzle09

import kotlin.math.max
import kotlin.math.min

internal class SnakeHelper {

    private var snakeGrid = mutableListOf<MutableList<String>>()
    private var positionsVisitedByHead = mutableListOf<SnakePosition>()
    private var positionsVisitedByTail = mutableListOf<SnakePosition>()
    private var snake = Snake(
        headPosition = SnakePosition(
            x = 0,
            y = 0,
        ),
        tailPosition = SnakePosition(
            x = 0,
            y = 0
        ),
        knots = mutableListOf(),
    )

    fun parseInputAndExecute(fileContent: String): List<List<String>> {
        println("parseInputAndExecute")
        initializeSnakeAndGrid()

        val fileLines = fileContent
            .replace("\r", "")
            .split("\n")
            .filter { line ->
                line.isNotBlank()
            }
            .map { line ->
                line.trim()
            }
        println("parseInputAndExecute ${"fileLines" to fileLines}")

        val snakeMovements = parseFileLinesToSnakeMovements(
            fileLines = fileLines,
        )

        println("parseInputAndExecute ${"snakeMovements" to snakeMovements}")


        positionsVisitedByHead += SnakePosition(
            x = snake.headPosition.x,
            y = snake.headPosition.y,
        )
        positionsVisitedByTail += SnakePosition(
            x = snake.tailPosition.x,
            y = snake.tailPosition.y,
        )
        snakeMovements
            .forEach { snakeMovement ->
                executeSnakeMovement(snakeMovement = snakeMovement)
//                draw()
//                println("________________________________")
            }

        val distinctPositionsForHead = positionsVisitedByHead.distinct()
        val distinctPositionsForTail = positionsVisitedByTail.distinct()

        draw()

        println("parseInputAndExecute ${"distinctPositionsForHead.size" to distinctPositionsForHead.size}")
        println("parseInputAndExecute ${"distinctPositionsForTail.size" to distinctPositionsForTail.size}")

        return snakeGrid
    }

    private fun draw() {
        return
        // Clear log
        print("\u001b[H\u001b[2J")

        // Draw
        snakeGrid.forEachIndexed { yIndex, strings ->
            strings.forEachIndexed { xIndex, s ->
                if (snake.tailPosition.x == xIndex
                    && snake.tailPosition.y == yIndex
                ) {
                    print(TAIL_SPACE)
                } else if (snake.headPosition.x == xIndex
                    && snake.headPosition.y == yIndex
                ) {
                    print(HEAD_SPACE)
                } else if (xIndex == GRID_X_SIZE / 2
                    && yIndex == GRID_Y_SIZE / 2
                ) {
                    print(START_SPACE)
                } else if (snake.knots.any { position ->
                        position.x == xIndex && position.y == yIndex
                    }) {
                    val index = snake.knots.indexOfFirst { position ->
                        position.x == xIndex && position.y == yIndex
                    }
                    print("${index + 1}")
                } else if (positionsVisitedByTail.any { position ->
                        position.x == xIndex && position.y == yIndex
                    }) {
                    print("#")
                } else if (positionsVisitedByHead.any { position ->
                        position.x == xIndex && position.y == yIndex
                    }) {
                    print("P")
                } else {
                    print(EMPTY_SPACE)
                }
            }
            println()
        }
    }

    private fun executeSnakeMovement(snakeMovement: SnakeMovement) {
        var xModifier = 0
        var yModifier = 0
        when (snakeMovement.direction) {
            SnakeMovementDirection.Down -> yModifier = 1
            SnakeMovementDirection.Left -> xModifier = -1
            SnakeMovementDirection.Right -> xModifier = 1
            SnakeMovementDirection.Up -> yModifier = -1
        }

        repeat(snakeMovement.numberToMove) {
            val previousHeadPosition = SnakePosition(
                x = snake.headPosition.x,
                y = snake.headPosition.y,
            )
            snake.headPosition.x += xModifier
            snake.headPosition.y += yModifier
            positionsVisitedByHead += SnakePosition(
                x = snake.headPosition.x,
                y = snake.headPosition.y,
            )

            // Update knots
            if (snake.headPosition.arePositionsTooFar(
                    otherPosition = snake.knots.first(),
                    maximumDistance = MAXIMUM_DISTANCE_FOR_KNOT,
                )
            ) {
                val modifierForFirstKnot =
                    snake.headPosition.getMovementForTwoPositions(otherPosition = snake.knots[0])

                snake.knots[0] = SnakePosition(
                    x = snake.knots[0].x + modifierForFirstKnot.first,
                    y = snake.knots[0].y + modifierForFirstKnot.second,
                )
                snake.knots.forEachIndexed { index, knot ->
                    if (index != 0
                        && snake.areKnotsTooFar(
                            firstKnotIndex = index,
                            secondKnotIndex = index - 1,
                            maximumDistance = MAXIMUM_DISTANCE_FOR_KNOT,
                        )
                    ) {
                        val (xModifierKnot, yModifierKnot) = snake.knots[index - 1].getMovementForTwoPositions(
                            otherPosition = knot
                        )
                        snake.knots[index] = SnakePosition(
                            x = knot.x + xModifierKnot,
                            y = knot.y + yModifierKnot,
                        )

                        draw()
                        println("________________________________")
                    }
                }
            } else {
                draw()
                println("________________________________")
            }

            positionsVisitedByTail += SnakePosition(
                x = snake.knots.last().x,
                y = snake.knots.last().y,
            )
        }
    }

    private fun SnakePosition.getMovementForTwoPositions(otherPosition: SnakePosition): Pair<Int, Int> {
        var xModifierKnot = 0
        var yModifierKnot = 0
        val (distanceX, distanceY) = this.getDistanceBetweenTwoPosition(otherPosition)

        // Same row
        if (distanceX == 0) {
            if (distanceY >= MAXIMUM_DISTANCE_FOR_KNOT) {
                yModifierKnot = 1
            } else if (distanceY <= -MAXIMUM_DISTANCE_FOR_KNOT) {
                yModifierKnot = -1
            }
        }
        // Same column
        else if (distanceY == 0) {
            if (distanceX >= MAXIMUM_DISTANCE_FOR_KNOT) {
                xModifierKnot = 1
            } else if (distanceX <= -MAXIMUM_DISTANCE_FOR_KNOT) {
                xModifierKnot = -1
            }
        }
        // Diagonal
        else {
            xModifierKnot = distanceX.coerceAtMost(1).coerceAtLeast(-1)
            yModifierKnot = distanceY.coerceAtMost(1).coerceAtLeast(-1)
        }
        return xModifierKnot to yModifierKnot
    }

    private fun parseFileLinesToSnakeMovements(fileLines: List<String>): List<SnakeMovement> {
        println("parseFileLinesToSnakeMovements")
        val snakeMovements = fileLines
            .map { line ->
                val splitted = line
                    .split(" ")
                SnakeMovement(
                    direction = SnakeMovementDirection.fromStringToValue(
                        givenString = splitted[0]
                    ),
                    numberToMove = splitted[1].toInt(),
                )
            }
        return snakeMovements
    }

    private fun initializeSnakeAndGrid() {
        println("initializeGrid")
        snakeGrid.clear()
        repeat(GRID_Y_SIZE) {
            val newLine = mutableListOf<String>()
            repeat(GRID_X_SIZE) {
                newLine += EMPTY_SPACE
            }
            snakeGrid += newLine
        }

        positionsVisitedByHead.clear()
        positionsVisitedByTail.clear()

        snake = Snake(
            headPosition = SnakePosition(
                x = GRID_X_SIZE / 2,
                y = GRID_Y_SIZE / 2,
            ),
            tailPosition = SnakePosition(
                x = GRID_X_SIZE / 2,
                y = GRID_Y_SIZE / 2
            ),
            knots = mutableListOf(),
        )
        snake.initializeKnots(MAXIMUM_DISTANCE - 1)
    }

    companion object {
        private const val HEAD_SPACE = "H"
        private const val TAIL_SPACE = "T"
        private const val START_SPACE = "S"
        private const val EMPTY_SPACE = "."
        private const val GRID_X_SIZE = 50
        private const val GRID_Y_SIZE = 50
        private const val MAXIMUM_DISTANCE_FOR_KNOT = 2
        private const val MAXIMUM_DISTANCE = 10
    }
}