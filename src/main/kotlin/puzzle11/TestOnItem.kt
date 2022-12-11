package puzzle11

data class TestOnItem(
    val testLines: List<String>,
    val divisorValue: ULong,
    val test: (ULong) -> Boolean,
    val monkeyTargetWhenTrue: Int,
    val monkeyTargetWhenFalse: Int,
)