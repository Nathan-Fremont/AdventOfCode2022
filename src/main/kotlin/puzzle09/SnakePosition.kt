package puzzle09

import kotlin.math.abs

data class SnakePosition(
    var x: Int,
    var y: Int,
) {
    fun getDistanceBetweenTwoPosition(otherPosition: SnakePosition): Pair<Int, Int> {
        val distanceX = this.x - otherPosition.x
        val distanceY = this.y - otherPosition.y

        return distanceX to distanceY
    }

    fun arePositionsTooFar(otherPosition: SnakePosition, maximumDistance: Int = 2): Boolean {
        val (distanceX, distanceY) = this.getDistanceBetweenTwoPosition(otherPosition = otherPosition)

        return abs(n = distanceX) >= maximumDistance || abs(n = distanceY) >= maximumDistance
    }

}