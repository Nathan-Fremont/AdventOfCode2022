package puzzle08

data class Tree(
    val x: Int,
    val y: Int,
    val height: Int,
    var isVisibleFromTop: Boolean = false,
    var isVisibleFromLeft: Boolean = false,
    var isVisibleFromRight: Boolean = false,
    var isVisibleFromBottom: Boolean = false,
    var numberOfTreesOnTop: Int = 0,
    var numberOfTreesOnLeft: Int = 0,
    var numberOfTreesOnRight: Int = 0,
    var numberOfTreesOnBottom: Int = 0,
    var calculatedScenicScore: Int = 0,
) {
    fun isVisibleFromOutside() = isVisibleFromTop
            || isVisibleFromLeft
            || isVisibleFromRight
            || isVisibleFromBottom

    val scenicScore: Int
        get() {
            val newValue = numberOfTreesOnTop * numberOfTreesOnLeft * numberOfTreesOnRight * numberOfTreesOnBottom
            calculatedScenicScore = newValue
            return newValue
        }
}