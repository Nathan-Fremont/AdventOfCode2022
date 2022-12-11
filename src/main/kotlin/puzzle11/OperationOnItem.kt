package puzzle11

data class OperationOnItem(
    val operationString: String,
    val operation: (Int) -> Int,
)