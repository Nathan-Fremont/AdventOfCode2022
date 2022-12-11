package puzzle11

data class Monkey(
    val monkeyAsString: String,
    val items: MutableList<ULong>,
    val operationOnItem: OperationOnItem,
    val testOnItem: TestOnItem,
)

