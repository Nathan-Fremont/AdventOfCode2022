package puzzle03

internal data class BagWithValue(
    val line: String,
    val containers: List<String>,
    val duplicatedChars: List<Char>,
    val valuesForContainers: List<Map<Char, Int>>,
)