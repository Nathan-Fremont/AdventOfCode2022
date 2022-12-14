package puzzle14

sealed class GridSquare {
    abstract var originalXValue: Int
    abstract var xValueInGrid: Int
    abstract var yValueInGrid: Int
    abstract val stringValue: String

    data class Air(
        override var originalXValue: Int,
        override var xValueInGrid: Int,
        override var yValueInGrid: Int,
    ): GridSquare() {
        override val stringValue: String
            get() = "."
    }

    data class Rock(
        override var originalXValue: Int,
        override var xValueInGrid: Int,
        override var yValueInGrid: Int,
    ): GridSquare() {
        override val stringValue: String
            get() = "#"
    }

    data class FallingSand(
        override var originalXValue: Int,
        override var xValueInGrid: Int,
        override var yValueInGrid: Int,
    ): GridSquare() {
        override val stringValue: String
            get() = "o"
    }

    data class FallingSandGenerator(
        override var originalXValue: Int,
        override var xValueInGrid: Int,
        override var yValueInGrid: Int,
    ): GridSquare() {
        override val stringValue: String
            get() = "+"
    }

    data class Void(
        override var originalXValue: Int,
        override var xValueInGrid: Int,
        override var yValueInGrid: Int,
    ): GridSquare() {
        override val stringValue: String
            get() = "~"
    }
}