package puzzle16

data class Valve(
    val lineValue: String,
    val valveLabel: String,
    val flowRate: Double,
    val tunnelsToLabels: List<String>,
    var isOpened: Boolean = false,
) {
    val tunnelsToValve: MutableList<Valve> = mutableListOf()
}