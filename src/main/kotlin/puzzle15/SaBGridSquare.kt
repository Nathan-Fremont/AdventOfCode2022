package puzzle15

import kotlin.math.abs

sealed class SaBGridSquare {
    abstract var originalXValue: Long
    abstract var originalYValue: Long
    abstract var xValueInGrid: Long
    abstract var yValueInGrid: Long
    abstract val stringValue: String
    abstract var distanceToClosestSensor: Long
    abstract var distanceToClosestBeacon: Long
    abstract var isBeaconPossible: Boolean

    data class None(
        override var originalXValue: Long,
        override var originalYValue: Long,
        override var xValueInGrid: Long = 0,
        override var yValueInGrid: Long = 0,
        override var distanceToClosestSensor: Long = -1,
        override var distanceToClosestBeacon: Long = -1,
        override var isBeaconPossible: Boolean = true,
    ) : SaBGridSquare() {
        override val stringValue: String
            get() = if (isBeaconPossible) {
                "."
            } else {
                "#"
            }
    }

    data class Sensor(
        override var originalXValue: Long,
        override var originalYValue: Long,
        override var xValueInGrid: Long = 0,
        override var yValueInGrid: Long = 0,
        override var distanceToClosestSensor: Long = 0,
        override var distanceToClosestBeacon: Long = -1,
        override var isBeaconPossible: Boolean = true,
    ) : SaBGridSquare() {
        override val stringValue: String
            get() = "S"
    }

    data class Beacon(
        override var originalXValue: Long,
        override var originalYValue: Long,
        override var xValueInGrid: Long = 0,
        override var yValueInGrid: Long = 0,
        override var distanceToClosestSensor: Long = -1,
        override var distanceToClosestBeacon: Long = 0,
        override var isBeaconPossible: Boolean = true,
    ) : SaBGridSquare() {
        override val stringValue: String
            get() = "B"
    }

    fun isOnSameOriginalPosition(otherSquare: SaBGridSquare): Boolean {
        return originalXValue == otherSquare.originalXValue
                && originalYValue == otherSquare.originalYValue
    }

    fun getDistanceBetweenTwoSquares(otherSquare: SaBGridSquare): Long {
        return abs(
            n = originalXValue - otherSquare.originalXValue,
        ) + abs(
            n = originalYValue - otherSquare.originalYValue,
        )
    }
}