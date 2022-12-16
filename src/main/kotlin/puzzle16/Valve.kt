package puzzle16

data class Valve(
    val lineValue: String,
    val valveLabel: String,
    val flowRate: Int,
    val tunnelsToLabels: List<String>,
    val tunnelsToValve: MutableList<Valve>,
)