package puzzle10

internal sealed interface Instruction {
    val cycleCost: Int
    object Noop: Instruction {
        override val cycleCost: Int
            get() = 1

        override fun toString(): String {
            return "Noop"
        }
    }

    data class Addx(
        val value: Int,
    ): Instruction {
        override val cycleCost: Int
            get() = 2
    }

    object Unknown: Instruction {
        override val cycleCost: Int
            get() = 0

        override fun toString(): String {
            return "Unknown"
        }
    }
}