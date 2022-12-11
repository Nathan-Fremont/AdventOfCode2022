package puzzle11

data class OperationOnItem(
    val operationString: String,
    val operation: (ULong) -> ULong,
)