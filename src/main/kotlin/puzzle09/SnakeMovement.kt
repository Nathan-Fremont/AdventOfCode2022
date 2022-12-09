package puzzle09

internal data class SnakeMovement(
    val direction: SnakeMovementDirection,
    val numberToMove: Int,
)

internal sealed class SnakeMovementDirection {
    abstract val stringValue: String

    object Up : SnakeMovementDirection() {
        override val stringValue: String
            get() = "U"
    }

    object Right : SnakeMovementDirection() {
        override val stringValue: String
            get() = "R"
    }

    object Down : SnakeMovementDirection() {
        override val stringValue: String
            get() = "D"
    }

    object Left : SnakeMovementDirection() {
        override val stringValue: String
            get() = "L"
    }

    companion object {
        fun fromStringToValue(givenString: String): SnakeMovementDirection = when (givenString) {
            Up.stringValue -> Up
            Right.stringValue -> Right
            Down.stringValue -> Down
            Left.stringValue -> Left
            else -> throw IllegalStateException()
        }
    }
}