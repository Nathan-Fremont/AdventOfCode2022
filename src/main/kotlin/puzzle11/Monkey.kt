package puzzle11

data class Monkey(
    val monkeyAsString: String,
    val items: MutableList<Int>,
    val operationOnItem: OperationOnItem,
    val testOnItem: TestOnItem,
)

