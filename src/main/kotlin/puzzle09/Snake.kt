package puzzle09

import kotlin.math.abs

internal data class Snake(
    var headPosition: SnakePosition,
    val knots: MutableList<SnakePosition>,
    var tailPosition: SnakePosition,
) {
    fun initializeKnots(maximumDistance: Int = 2) {
        knots.clear()
        repeat(maximumDistance) {
            knots += SnakePosition(
                x = headPosition.x,
                y = headPosition.y
            )
        }
    }

    fun getDistanceBetweenHeadAndTail(givenTailPosition: SnakePosition = tailPosition): Pair<Int, Int> {
        return headPosition.getDistanceBetweenTwoPosition(otherPosition = givenTailPosition)
    }

    fun getDistanceBetweenTwoKnots(firstKnot: SnakePosition, secondKnot: SnakePosition): Pair<Int, Int> {
        return firstKnot.getDistanceBetweenTwoPosition(otherPosition = secondKnot)
    }

    fun isHeadTooFarFromTail(givenTailPosition: SnakePosition = tailPosition, maximumDistance: Int = 2): Boolean {
        val (distanceX, distanceY) = getDistanceBetweenHeadAndTail(givenTailPosition = givenTailPosition)

        return abs(n = distanceX) >= maximumDistance || abs(n = distanceY) >= maximumDistance
    }

    fun areKnotsTooFar(firstKnotIndex: Int, secondKnotIndex: Int, maximumDistance: Int = 2): Boolean {
        val (distanceX, distanceY) = getDistanceBetweenTwoKnots(knots[firstKnotIndex], knots[secondKnotIndex])

        return abs(n = distanceX) >= maximumDistance || abs(n = distanceY) >= maximumDistance
    }
}