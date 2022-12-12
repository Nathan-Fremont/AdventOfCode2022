package puzzle12

data class Hill(
    val stringValue: String,
    val isStart: Boolean,
    val isEnd: Boolean,
    val elevation: Int,
    val xValue: Int,
    val yValue: Int,
)