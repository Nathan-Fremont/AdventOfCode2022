package puzzle11

data class TestOnItem(
    val testLines: List<String>,
    val test: (Int) -> Boolean,
    val monkeyTargetWhenTrue: Int,
    val monkeyTargetWhenFalse: Int,
)